package tfw.tsm;

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.value.ValueException;

/**
 * The base class for all event handling components which participate in the
 * processing phase of transactions.
 */
public abstract class Processor extends RollbackHandler {
    Processor(
            String name,
            EventChannelDescription[] triggeringSinks,
            EventChannelDescription[] nonTriggeringSinks,
            EventChannelDescription[] sources) {
        super(name, triggeringSinks, nonTriggeringSinks, sources);
    }

    @Override
    void stateChange(EventChannel eventChannel) {
        getTransactionManager().addProcessor(this);
    }

    /**
     * Returns the state of the specified event channel prior to the current
     * state change cycle.
     *
     * @param sinkEventChannel
     *            the sink event channel whose state is to be returned.
     * @return the state of the event channel during the previous state change
     *         cycle.
     */
    protected final Object getPreviousCycleState(EventChannelDescription sinkEventChannel) {
        Argument.assertNotNull(sinkEventChannel, "sinkEventChannel");
        assertNotStateless(sinkEventChannel);
        Sink sink = getSink(sinkEventChannel);

        if (sink == null) {
            throw new IllegalArgumentException(sinkEventChannel.getEventChannelName() + " not found");
        }

        if (sink.eventChannel == null) {
            throw new IllegalStateException(sinkEventChannel + " is not connected to an event channel");
        }

        return sink.eventChannel.getPreviousCycleState();
    }

    /**
     * Asynchronously changes the state of the specified event channel.
     *
     * @param sourceEventChannel
     *            the name of the event channel.
     * @param state
     *            the new value for the event channel.
     * @throws IllegalArgumentException
     *             if the specified state violates the value constraints of the
     *             event channel.
     */
    protected final void set(EventChannelDescription sourceEventChannel, Object state) {
        Source source = getSource(sourceEventChannel.getEventChannelName());

        if (source == null) {
            throw new IllegalArgumentException(
                    "Source event channel '" + sourceEventChannel + "' not found in " + getFullyQualifiedName());
        }
        if (!(sourceEventChannel instanceof StatelessTriggerECD) && state == null) {
            throw new IllegalArgumentException(
                    "Cannot set null on event channel " + sourceEventChannel.getEventChannelName());
        }

        try {
            source.setState(state);
        } catch (ValueException ve) {
            throw new IllegalArgumentException(ve.getMessage());
        }
    }

    /**
     * Causes a state-less event channel to fire.
     *
     * @param triggerECD
     *            the event channel to be fired.
     */
    protected final void trigger(StatelessTriggerECD triggerECD) {
        set(triggerECD, null);
    }

    /**
     * This method is called in any transaction in which one or more of the
     * sinks specified at construction receives new state.
     */
    abstract void process();
}
