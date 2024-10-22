package tfw.tsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import tfw.check.Argument;
import tfw.tsm.MultiplexerStrategy.MultiStateAccessor;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;

public class Multiplexer implements EventChannel {
    private final MultiplexerStrategy multiStrategy;

    /** The branch associated with this terminator. */
    private MultiplexedBranch component = null;

    /** The description of the single value event channel. */
    final ObjectECD valueECD;

    /** The state change rule for the sub-channels. */
    private final StateChangeRule stateChangeRule;

    /** The multiplexed value sink. */
    final Sink multiSink;

    /** The multiplexed value initiator source. */
    public final MultiSource processorMultiSource;

    /** The set of demultiplexing event channels. */
    private final Map<Object, DemultiplexedEventChannel> demultiplexedEventChannels =
            new HashMap<Object, DemultiplexedEventChannel>();

    /**
     * Creates a multiplexer with the specified value and multi-value event channels.
     *
     * @param multiplexerBranchName the name of the multiplexer branch this multiplexer is associated with.
     * @param valueECD the event channel description of the individual multiplexed elements.
     * @param multiValueECD the event channel description of the entire multiplexed element.
     * @param stateChangeRule the state change rule that determines when state has changed.
     * @param multiStrategy the strategy responsible for managing the multiplexed elements.
     */
    Multiplexer(
            String multiplexerBranchName,
            ObjectECD valueECD,
            ObjectECD multiValueECD,
            StateChangeRule stateChangeRule,
            MultiplexerStrategy multiStrategy) {
        Argument.assertNotNull(valueECD, "valueECD");
        Argument.assertNotNull(multiValueECD, "multiValueECD");
        Argument.assertNotNull(multiStrategy, "multiStrategy");

        this.valueECD = valueECD;
        this.stateChangeRule = stateChangeRule;
        this.multiSink = new MultiSink(multiValueECD);
        this.processorMultiSource = new MultiSource("Multiplexer Source[" + multiplexerBranchName + "]", multiValueECD);
        this.multiStrategy = multiStrategy;
    }

    public class MultiSink extends Sink {
        public MultiSink(ObjectECD ecd) {
            super("MultiplexSink[" + ecd.getEventChannelName() + "]", ecd, true);
        }

        /**
         * @see co2.ui.fw.Sink#stateChange()
         */
        @Override
        void stateChange() {
            if (processorMultiSource.isStateSource()) {
                return;
            }

            MultiStateAccessor stateAccessor = multiStrategy.toMultiStateAccessor(getState());

            for (DemultiplexedEventChannel dm : Multiplexer.this.demultiplexedEventChannels.values()) {
                Object state = stateAccessor.getState(dm.demultiplexSlotId);
                if (state != null && stateChangeRule.isChange(dm.getPreviousCycleState(), state)) {
                    dm.setDeMultiState(state);
                }
            }
        }

        public Iterator<DemultiplexedEventChannel> getDemultiplexedEventChannels() {
            return demultiplexedEventChannels.values().iterator();
        }
    }

    class MultiSource extends ProcessorSource {
        ArrayList<DemultiplexedEventChannel> pendingStateChanges = new ArrayList<DemultiplexedEventChannel>();

        MultiSource(String name, EventChannelDescription ecd) {
            super(name, ecd);
        }

        void setState(DemultiplexedEventChannel deMultiplexer) {
            if (!pendingStateChanges.contains(deMultiplexer)) {
                pendingStateChanges.add(deMultiplexer);
            }
            fire();
        }

        private int keyValueArraySize = 0;
        private Object[] keys = new Object[keyValueArraySize];
        private Object[] values = new Object[keyValueArraySize];

        @Override
        Object fire() {
            if (pendingStateChanges.size() == 0) {
                throw new IllegalStateException("No pending state changes to fire.");
            }

            keyValueArraySize = pendingStateChanges.size();
            if (keys.length < keyValueArraySize) {
                keys = new Object[keyValueArraySize];
                values = new Object[keyValueArraySize];
            }

            for (int i = 0; i < keyValueArraySize; i++) {
                DemultiplexedEventChannel dec = pendingStateChanges.get(i);

                keys[i] = dec.demultiplexSlotId;
                values[i] = dec.getState();
            }
            pendingStateChanges.clear();

            Object multiState = null;

            try {
                multiState = Multiplexer.this.multiStrategy.addToMultiState(
                        eventChannel.getState(), keys, values, keyValueArraySize);
            } catch (Exception e) {
                throw new RuntimeException("ECD=" + ecd.getEventChannelName(), e);
            }

            eventChannel.setState(this, multiState, null);

            getTreeComponent().getTransactionManager().addChangedEventChannel(eventChannel);

            return multiState;
        }
    }

    private EventChannel getDemultiplexedEventChannel(Object slotId) {
        if (slotId == null) {
            throw new IllegalArgumentException("slotId == null not allowed");
        }

        if (!demultiplexedEventChannels.containsKey(slotId)) {
            Object currentSlotState = null;
            /*
             * TODO: If the multiSink is not connected, initial state is not
             * handled properly.
             */
            if (this.multiSink.isConnected()) {
                Object multiState = getState();
                MultiStateAccessor msa = this.multiStrategy.toMultiStateAccessor(multiState);
                currentSlotState = msa.getState(slotId);
            }
            final Object defaultSlotState = this.multiStrategy.getDefaultSlotState();
            Object initialSlotState = currentSlotState;
            if (initialSlotState == null) {
                initialSlotState = defaultSlotState;
            }

            DemultiplexedEventChannel dm =
                    new DemultiplexedEventChannel(this, slotId, initialSlotState, stateChangeRule);
            /*
             * if the initial slot state is non-null, make sure the state gets
             * added to the multiplexed state.
             */
            if (this.multiSink.isConnected() && currentSlotState == null && defaultSlotState != null) {
                this.processorMultiSource.setState(dm);
            }

            dm.setTreeComponent(this.component);
            demultiplexedEventChannels.put(slotId, dm);
        }

        return demultiplexedEventChannels.get(slotId);
    }

    TreeComponent getTreeComponent() {
        return this.component;
    }

    void remove(DemultiplexedEventChannel deMultiplexer) {
        if (demultiplexedEventChannels.remove(deMultiplexer.demultiplexSlotId) == null) {
            throw new IllegalStateException("Demultiplexer not found attempting to remove demultiplexer for "
                    + valueECD.getEventChannelName() + "["
                    + deMultiplexer.demultiplexSlotId + "]");
        }
    }

    /**
     * @see tfw.tsm.EventChannel#add(tfw.tsm.Port)
     */
    @Override
    public void add(Port port) {
        // Search for the multiplexed component...
        Object slotId = component.getSlotId(port.getTreeComponent());
        TreeComponent tc = port.getTreeComponent().getParent();
        while (slotId == null && tc != null) {
            slotId = component.getSlotId(tc);
            tc = tc.getParent();
        }

        // if we didn't find a multiplexed component...
        if (slotId == null) {
            throw new IllegalArgumentException("The specified port, '"
                    + port.ecd.getEventChannelName()
                    + "', is not from a multiplexed component.\n" + " p.tc="
                    + port.getTreeComponent() + " p.fqn="
                    + port.getFullyQualifiedName());
        }

        getDemultiplexedEventChannel(slotId).add(port);
    }

    /**
     * @see tfw.tsm.EventChannel#addDeferredStateChange(tfw.tsm.ProcessorSource)
     */
    @Override
    public void addDeferredStateChange(ProcessorSource source) {
        throw new UnsupportedOperationException("Multiplexer does not participate directly in transactions.");
    }

    /**
     * @see tfw.tsm.EventChannel#fire()
     */
    @Override
    public Object fire() {
        throw new UnsupportedOperationException("Multiplexer does not participate directly in transactions.");
    }

    /**
     * @see tfw.tsm.EventChannel#getParent()
     */
    @Override
    public TreeComponent getParent() {
        return this.component;
    }

    /**
     * @see tfw.tsm.EventChannel#getCurrentStateSource()
     */
    @Override
    public Source getCurrentStateSource() {
        throw new UnsupportedOperationException("Multiplexer does not participate directly in transactions.");
    }

    /**
     * @see tfw.tsm.EventChannel#getECD()
     */
    @Override
    public EventChannelDescription getECD() {
        return valueECD;
    }

    /**
     * @see tfw.tsm.EventChannel#getPreviousCycleState()
     */
    @Override
    public Object getPreviousCycleState() {
        throw new UnsupportedOperationException("Multiplexer does not participate directly in transactions.");
    }

    /**
     * @see tfw.tsm.EventChannel#getPreviousTransactionState()
     */
    @Override
    public Object getPreviousTransactionState() {
        throw new UnsupportedOperationException("Multiplexer does not participate directly in transactions.");
    }

    @Override
    public boolean isStateChanged() {
        return false;
    }

    /**
     * @see tfw.tsm.EventChannel#getState()
     */
    @Override
    public Object getState() {
        return this.multiSink.eventChannel.getState();
    }

    /**
     * @see tfw.tsm.EventChannel#isFireOnConnect()
     */
    @Override
    public boolean isFireOnConnect() {
        throw new UnsupportedOperationException("Multiplexer does not participate directly in transactions.");
    }

    /**
     * @see tfw.tsm.EventChannel#isRollbackParticipant()
     */
    @Override
    public boolean isRollbackParticipant() {
        throw new UnsupportedOperationException("Multiplexer does not participate directly in transactions.");
    }

    /**
     * @see tfw.tsm.EventChannel#remove(tfw.tsm.Port)
     */
    @Override
    public void remove(Port port) {
        port.eventChannel.remove(port);
    }

    /**
     * @see tfw.tsm.EventChannel#setState(tfw.tsm.Source, java.lang.Object,
     *      tfw.tsm.EventChannel)
     */
    @Override
    public void setState(Source source, Object state, EventChannel forwardingEventChannel) {
        throw new UnsupportedOperationException("Multiplexer does not participate directly in transactions.");
    }

    /**
     * @see tfw.tsm.EventChannel#setTreeComponent(tfw.tsm.TreeComponent)
     */
    @Override
    public void setTreeComponent(TreeComponent component) {
        this.component = (MultiplexedBranch) component;
        this.multiSink.setTreeComponent(component);
        this.processorMultiSource.setTreeComponent(component);
    }

    /**
     * @see tfw.tsm.EventChannel#synchronizeCycleState()
     */
    @Override
    public void synchronizeCycleState() {
        throw new UnsupportedOperationException("Multiplexer does not participate directly in transactions.");
    }

    /**
     * @see tfw.tsm.EventChannel#synchronizeTransactionState()
     */
    @Override
    public void synchronizeTransactionState() {
        throw new UnsupportedOperationException("Multiplexer does not participate directly in transactions.");
    }
}
