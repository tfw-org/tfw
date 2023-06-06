package tfw.tsm.ecd;

import tfw.check.Argument;
import tfw.value.ValueConstraint;

/**
 * Describes an event channel. For specialized event channel descriptions
 * extends {@link ObjectECD}.
 */
public abstract class EventChannelDescription {
    /** The name of the event channel. */
    private final String eventChannelName;

    /** The value constraint for this event channel. */
    private final ValueConstraint constraint;

    /** A flag indicating whether the event channel fires on connection. */
    private final boolean fireOnConnect;

    /** A flag indicating whether the event channel participates in rollbacks. */
    private final boolean rollbackParticipant;

    /**
     * Creates an event channel description with the specified attributes.
     *
     * @param eventChannelName
     *            the name of the event channel.
     * @param constraint
     *            the value constraint for the event channel.
     */
    EventChannelDescription(String eventChannelName, ValueConstraint constraint) {
        this(eventChannelName, constraint, true, true);
    }

    /**
     * Creates an event channel description with the specified attributes.
     *
     * @param eventChannelName
     *            the name of the event channel.
     * @param constraint
     *            the value constraint for the evnet channel.
     * @param codec
     *            the codec for the event channel values. <code>null</code> is
     *            a valid value.
     * @param fireOnConnect
     *            flag indicating whether the event channel fires state when a
     *            new sink is connected.
     * @param rollbackParticipant
     *            flag indicating whether the event channel participates in
     *            transaction rollbacks.
     */
    EventChannelDescription(
            String eventChannelName, ValueConstraint constraint, boolean fireOnConnect, boolean rollbackParticipant) {
        Argument.assertNotNull(eventChannelName, "eventChannelName");
        Argument.assertNotNull(constraint, "constraint");
        // CheckArgument.checkNull(codec, "codec");

        this.eventChannelName = eventChannelName.trim();
        this.constraint = constraint;
        this.fireOnConnect = fireOnConnect;
        this.rollbackParticipant = rollbackParticipant;

        if (this.eventChannelName.length() == 0) {
            throw new IllegalArgumentException("eventChannelName.trim().length() == 0 not allowed!");
        }
    }

    /**
     * Returns the event channel name.
     *
     * @return the event channel name.
     */
    public final String getEventChannelName() {
        return eventChannelName;
    }

    /**
     * Returns the value constraint.
     *
     * @return the value constraint.
     */
    public final ValueConstraint getConstraint() {
        return constraint;
    }

    /**
     * Returns true if the event channel fires on connection, false otherwise.
     *
     * @return true if the event channel fires on connection, false otherwise.
     */
    public boolean isFireOnConnect() {
        return fireOnConnect;
    }

    /**
     * Returns true if the event channel participates in rollbacks, false
     * otherwise.
     *
     * @return true if the event channel participates in rollbacks, false
     *         otherwise.
     */
    public boolean isRollBackParticipant() {
        return rollbackParticipant;
    }

    /**
     * Returns a hash value for this ecd.
     */
    public int hashCode() {
        return this.eventChannelName.hashCode();
    }

    /**
     * Checks for equality between the specified object and this ecd.
     *
     * @param object
     *            The object to be tested.
     * @return true if the object is equivalent otherwise returns false.
     */
    public boolean equals(Object object) {
        if (!(object instanceof EventChannelDescription)) {
            return false;
        }

        EventChannelDescription ecd = (EventChannelDescription) object;

        return ecd.eventChannelName.equals(this.eventChannelName)
                && ecd.constraint == this.constraint
                && ecd.fireOnConnect == this.fireOnConnect
                && ecd.rollbackParticipant == this.rollbackParticipant;
    }

    /**
     * Returns a string representation of the event channel description.
     */
    public String toString() {
        return super.toString() + "[eventChannelName = " + this.eventChannelName + "]";
    }
}
