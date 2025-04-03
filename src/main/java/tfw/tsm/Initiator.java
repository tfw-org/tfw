package tfw.tsm;

import java.util.ArrayList;
import java.util.List;
import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.value.ValueException;

/**
 * This class provides an interface to generate a transaction and to set the
 * state of event channels in that transaction. The initiator will queue state
 * changes if it is not rooted. The queueing strategy can be set by providing
 * the appropriate {@link StateQueueFactory} on the constructor
 * {@link #Initiator(String, EventChannelDescription[], StateQueueFactory)}.
 * The default queuing strategy employs an unbounded queue which will store up
 * state changes until the initiator becomes rooted and can begin to fire its
 * state changes. Note that a component is said to be rooted when it is attached
 * to a root component or its parent is rooted.
 * <P>
 * State Change Rules:
 * <ol>
 * <li>If one of the 'set' or 'trigger' methods is called while the
 * <tt>Initiator</tt> is rooted, the state changes will be immediately
 * scheduled with the transaction manager.</li>
 * <li>If one of the 'set' or 'trigger' methods is called while the
 * <tt>Initiator</tt> is not rooted the state changes will be queued,
 * according to the queuing strategy supplied at construction, and deferred
 * until such time as the <tt>Initiator</tt> becomes rooted.</li>
 * <li>An <tt>Initiator</tt> will schedule all of its deferred state changes
 * with the transaction manager immediately upon becoming rooted. </li>
 * <li>If the <tt>Initiator</tt> becomes un-rooted after state changes have
 * been scheduled with the transaction manager and before the state changes have
 * occurred, the state changes will occur if and only if the associated event
 * channel is rooted when the state change transaction is executed. </li>
 * </ol>
 */
public class Initiator extends TreeComponent {
    /**
     * The list of state changes which occur while the component is not
     * connected.
     */
    private List<TransactionContainer> deferredStateChanges = null;

    /**
     * Constructs an <code>Initiator</code> with the specified name and source
     * event channel.
     *
     * @param name
     *            the non-null name for the <code>Initiator</code>
     * @param source
     *            the source event channel for this initiator.
     */
    public Initiator(String name, EventChannelDescription source) {
        this(name, new EventChannelDescription[] {source});
    }

    /**
     * Constructs an <code>Initiator</code> with the specified name and set of
     * source event channels.
     *
     * @param name
     *            the non-null name for the <code>Initiator</code>
     * @param sources
     *            A non-null, non-empty array of source event channels for the
     *            <code>Initiator</code>.
     */
    public Initiator(String name, EventChannelDescription[] sources) {
        this(name, sources, null);
    }

    /**
     * Constructs an <code>Initiator</code> with the specified name, set of
     * source event channels, and state queue.
     *
     * @param name
     *            the non-null name for the <code>Initiator</code>
     * @param sources
     *            A non-null, non-empty array of source event channels for the
     *            <code>Initiator</code>.
     * @param queueFactory
     *            a factory for creating state queue for each source event
     *            channel.
     */
    public Initiator(String name, EventChannelDescription[] sources, StateQueueFactory queueFactory) {
        super(
                name,
                null,
                createSources(name, sources, queueFactory == null ? new DefaultStateQueueFactory() : queueFactory),
                null);

        Argument.assertNotNull(name, "name");

        if (sources.length == 0) {
            throw new IllegalArgumentException("sources.length == 0 not allowed");
        }
    }

    public static InitiatorBuilder builder() {
        return new InitiatorBuilder();
    }

    synchronized TransactionContainer[] getDeferredStateChangesAndClear() {
        if (deferredStateChanges != null) {
            TransactionContainer[] sns =
                    deferredStateChanges.toArray(new TransactionContainer[deferredStateChanges.size()]);

            deferredStateChanges = null;

            return sns;
        } else {
            return null;
        }
    }

    private static Source[] createSources(String name, EventChannelDescription[] sources, StateQueueFactory factory) {
        Argument.assertNotNull(sources, "sources");
        Argument.assertElementNotNull(sources, "sources");
        Argument.assertNotNull(factory, "factory");

        Source[] srcs = new Source[sources.length];

        for (int i = 0; i < sources.length; i++) {
            srcs[i] = new InitiatorSource(name, sources[i], factory.create());
        }

        return srcs;
    }

    private TransactionState newTransaction(InitiatorSource[] sources, Object[] state) {
        Root localImmediateRoot = null;
        BranchComponent localImmediateParent = null;

        synchronized (this) {
            localImmediateRoot = immediateRoot;
            localImmediateParent = immediateParent;
        }

        if (localImmediateRoot == null) {
            TransactionState transactionState = new DeferredTransactionState();

            if (localImmediateParent == null) {
                synchronized (this) {
                    if (deferredStateChanges == null) {
                        deferredStateChanges = new ArrayList<TransactionContainer>();
                    }

                    deferredStateChanges.add(
                            new TransactionContainer(new SourceNState(sources, state), transactionState));
                }
            } else {
                localImmediateParent.addStateChange(
                        new TransactionContainer(new SourceNState(sources, state), transactionState));
            }

            return transactionState;
        } else {
            TransactionState transactionState = localImmediateRoot
                    .getTransactionManager()
                    .getTransactionQueue()
                    .createTransactionState();

            localImmediateRoot
                    .getTransactionManager()
                    .addStateChange(
                            sources,
                            state,
                            transactionState,
                            TransactionMgr.isTraceLogging() ? new Throwable("StateChange") : null);

            return transactionState;
        }
    }

    /**
     * Sets, asynchronously, the state of the specified event channel to the
     * specified state. Note that the <code>sourceEventChannel</code> will be
     * updated in transaction manager event queue thread regardless of what
     * thread calls this method.
     *
     * @param sourceEventChannel
     *            The event channel to be updated. It must be one of the event
     *            channels specified at construction.
     *
     * @param state
     *            the new state for the event channel.
     */
    private TransactionState unifiedSet(EventChannelDescription sourceEventChannel, final Object state) {

        Argument.assertNotNull(sourceEventChannel, "sourceEventChannel");
        // Trigger have null values...
        // CheckArgument.checkNull(state, "state");

        final InitiatorSource source = (InitiatorSource) getSource(sourceEventChannel.getEventChannelName());

        if (source == null) {
            throw new IllegalArgumentException(sourceEventChannel + " not found");
        }

        try {
            source.ecd.getConstraint().checkValue(state);
        } catch (ValueException ve) {
            throw new IllegalArgumentException(ve.getMessage());
        }
        return newTransaction(new InitiatorSource[] {source}, new Object[] {state});
    }

    /**
     * Sets the state of the eventChannels with values specified.
     *
     * @param state
     *            the event channel state to set.
     */
    public final void set(EventChannelState[] state) {
        fset(state);
    }

    public final TransactionState fset(EventChannelState[] state) {
        Argument.assertNotEmpty(state, "state");

        InitiatorSource[] sources = new InitiatorSource[state.length];
        Object[] stateObjects = new Object[state.length];

        for (int i = 0; i < state.length; i++) {
            String eventChannel = state[i].getEventChannelName();
            InitiatorSource source = (InitiatorSource) getSource(eventChannel);

            if (source == null) {
                throw new IllegalArgumentException(eventChannel + " not found");
            }

            sources[i] = source;
            stateObjects[i] = state[i].getState();
        }

        return newTransaction(sources, stateObjects);
    }

    /**
     * Sets, asynchronously, the state of the specified event channel to
     * <code>new Object()</code>. This is intended to be used to activate
     * {@link TriggeredConverter}s where the state changes is not relevant.
     *
     * @param triggerEventChannel
     *            The event channel to receive the trigger event.
     */
    public final void trigger(StatelessTriggerECD triggerEventChannel) {
        ftrigger(triggerEventChannel);
    }

    public final TransactionState ftrigger(StatelessTriggerECD triggerEventChannel) {
        return unifiedSet(triggerEventChannel, null);
    }

    public final void set(ObjectECD objectECD, Object state) {
        fset(objectECD, state);
    }

    public final TransactionState fset(ObjectECD objectECD, Object state) {
        return unifiedSet(objectECD, state);
    }

    static class SourceNState {
        public final InitiatorSource[] sources;
        public final Object[] state;

        public SourceNState(InitiatorSource[] sources, Object[] state) {
            this.sources = sources;
            this.state = state;
        }
    }

    static class TransactionContainer {
        public final SourceNState state;
        public final TransactionState transactionState;
        public final Throwable setLocation;

        public TransactionContainer(SourceNState state, TransactionState transactionState) {
            this.state = state;
            this.transactionState = transactionState;
            this.setLocation = TransactionMgr.isTraceLogging() ? new Throwable("StateChange") : null;
        }
    }

    public static class InitiatorBuilder {
        private String name = null;
        private List<EventChannelDescription> eventChannelDescriptions = new ArrayList<>();
        private StateQueueFactory stateQueueFactory = new DefaultStateQueueFactory();

        InitiatorBuilder() {}

        public InitiatorBuilder setName(final String name) {
            this.name = name;

            return this;
        }

        public InitiatorBuilder addEventChannelDescription(final EventChannelDescription eventChannelDescription) {
            eventChannelDescriptions.add(eventChannelDescription);

            return this;
        }

        public InitiatorBuilder setStateQueueFactory(final StateQueueFactory stateQueueFactory) {
            this.stateQueueFactory = stateQueueFactory;

            return this;
        }

        public Initiator build() {
            Argument.assertNotNull(name, "name");
            Argument.assertGreaterThan(eventChannelDescriptions.size(), 0, "eventChannelDescriptions.size()");

            final EventChannelDescription[] ecds =
                    eventChannelDescriptions.toArray(new EventChannelDescription[eventChannelDescriptions.size()]);

            return new Initiator(name, ecds, stateQueueFactory);
        }
    }
}
