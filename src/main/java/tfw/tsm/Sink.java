package tfw.tsm;

import tfw.tsm.ecd.EventChannelDescription;

/**
 * An event sink.
 */
public abstract class Sink extends Port {
    private final boolean isTriggering;

    /**
     * Constructs a sink with the specified attributes.
     *
     * @param name the name of the sink.
     * @param ecd the event channel description.
     * @param isTriggering whether or not a state change on the event channel causes the sink's component to participate in the transaction.
     */
    Sink(String name, EventChannelDescription ecd, boolean isTriggering) {
        super(name, ecd);
        this.isTriggering = isTriggering;
    }

    public boolean isTriggering() {
        return isTriggering;
    }

    @Override
    public String toString() {
        return "dew.ui.frmwrk.Sink[name = " + getFullyQualifiedName() + ", eventChannelName = "
                + ecd.getEventChannelName()
                + "]";
    }

    /**
     * Called when the event channel state is modified.
     */
    abstract void stateChange();
}
