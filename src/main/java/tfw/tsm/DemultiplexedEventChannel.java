package tfw.tsm;

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.value.ValueException;

/**
 * The event channel for sub-channels under a multiplexer.
 */
public class DemultiplexedEventChannel extends Terminator {
    /** The parent multiplexer. */
    private final Multiplexer parent;

    /**
     * The source for pushing events from above the multiplexer down into the
     * sub-channel.
     */
    private final ProcessorSource deMultiSource;

    /** The sub-channel identifier of this event channel. */
    final Object demultiplexSlotId;

    /** The number of ports connected to this event channel. */
    private int connectionCount = 0;

    /**
     * Creates an event channel for a specific sub-channel.
     *
     * @param parent
     *            The parent multiplexer.
     * @param demultiplexSlotId
     *            The sub-channel identifier of this event channel.
     * @param stateChangeRule
     *            The state change rule for this event channel.
     */
    DemultiplexedEventChannel(
            Multiplexer parent, Object demultiplexSlotId, Object initialState, StateChangeRule stateChangeRule) {
        super(parent.valueECD, initialState, stateChangeRule);
        Argument.assertNotNull(parent, "parent");
        this.parent = parent;
        this.demultiplexSlotId = demultiplexSlotId;
        this.deMultiSource =
                new DemultiSource(parent.processorMultiSource.name + "[" + demultiplexSlotId + "]", parent.valueECD);
        this.deMultiSource.setTreeComponent(parent.getTreeComponent());
        super.add(this.deMultiSource);
    }

    /**
     * Overrides the Terminator method in order to propogate the state up
     * through the multiplexer.
     */
    public void setState(Source source, Object state, EventChannel forwardingEventChannel) {
        super.setState(source, state, forwardingEventChannel);
        if (this.component != source.getTreeComponent()) {
            parent.processorMultiSource.setState(this);
        }
    }

    /**
     * Overrides the Terminator method inorder to count connections.
     */
    public void add(Port port) {
        super.add(port);
        connectionCount++;
    }

    /**
     * Overrides the Terminator method in order to count connections and remove
     * this demultiplexer event channel when connections reach zero.
     */
    public void remove(Port port) {
        super.remove(port);
        connectionCount--;

        // if no ports are connected...
        if (connectionCount == 0) {
            // remove this event channel so it can be garbage collected.
            parent.remove(this);
        }
    }

    public Multiplexer getMultiplexer() {
        return parent;
    }

    /**
     * Called by MultiSink to push state into the demultiplexed sub-channel.
     *
     * @param state
     *            The state value for this event channel.
     */
    void setDeMultiState(Object state) {
        if (state == null) {
            throw new IllegalStateException("Demultiplexing error, attempt to set state to null in sub-channel '"
                    + this.demultiplexSlotId
                    + "' of multiplexed channel '"
                    + parent.multiSink.ecd.getEventChannelName() + "'");
        }
        this.deMultiSource.setState(state);
    }

    class DemultiSource extends ProcessorSource {
        private int fireCount = 0;

        DemultiSource(String name, EventChannelDescription ecd) {
            super(name, ecd);
        }

        Object getState() {
            Object state = super.getState();
            return state;
        }

        void setState(Object state) throws ValueException {
            Argument.assertNotNull(state, "state");
            if (this.getState() != null) {
                // (new IllegalStateException("Attempt to overwrite state "
                // +this)).printStackTrace();
            }
            this.fireCount = 0;
            super.setState(state, false);
        }

        DemultiplexedEventChannel getDemultiplexedEventChannel() {
            return DemultiplexedEventChannel.this;
        }

        Object fire() {
            this.fireCount++;
            if (getState() == null) {
                // (new IllegalStateException(
                // "Attempt to fire null state(fireCount == "
                // + fireCount + ") on event channel: "
                // + this.getEventChannelName()+" - "
                // + this)).printStackTrace();
                return null;
            }
            return super.fire();
        }
    }
}
