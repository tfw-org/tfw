package tfw.tsm.ecd;

import tfw.value.ValueConstraint;

/**
 * The base class for all event channels that are used to set state as part of a
 * transaction rollback. Note that the state is changed in a follow-on
 * transaction.
 */
public abstract class RollbackECD extends ObjectECD {
    /**
     * Creates a rollback event channel description.
     *
     * @param eventChannelName
     *            the name of the event channel.
     * @param constraint
     *            the value constraint for the evnet channel.
     */
    protected RollbackECD(String eventChannelName, ValueConstraint<? extends Object> constraint) {
        super(eventChannelName, constraint);
    }
}
