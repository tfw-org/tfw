package tfw.tsm.ecd;

import java.util.function.Predicate;
import tfw.check.Argument;

/**
 * Describes an event channel, including the predicate used to validate
 * values sent through the event channel.
 *
 * <p>The predicate is also used when comparing event channel descriptions.
 * Predicates that represent value-based validation should therefore override
 * {@link Object#equals(Object)} and {@link Object#hashCode()} so that
 * independently created predicates with equivalent validation semantics
 * compare equal.</p>
 */
public abstract class EventChannelDescription {
    /** The name of the event channel. */
    private final String eventChannelName;

    /**
     * The predicate used to validate values for this event channel.
     */
    private final Predicate<Object> predicate;

    /** A flag indicating whether the event channel fires on connection. */
    private final boolean fireOnConnect;

    /** A flag indicating whether the event channel participates in rollbacks. */
    private final boolean rollbackParticipant;

    /**
     * Creates an event channel description.
     *
     * @param name the name of the event channel.
     * @param predicate the predicate used to validate values for the event channel.
     */
    EventChannelDescription(String eventChannelName, Predicate<Object> predicate) {
        this(eventChannelName, predicate, true, true);
    }

    /**
     * Creates an event channel description with the specified attributes.
     *
     * @param eventChannelName the name of the event channel.
     * @param predicate the predicate used to validate values for the event channel.
     * @param fireOnConnect flag indicating whether the event channel fires state when a new sink is connected.
     * @param rollbackParticipant flag indicating whether the event channel participates in transaction rollbacks.
     */
    EventChannelDescription(
            String eventChannelName, Predicate<Object> predicate, boolean fireOnConnect, boolean rollbackParticipant) {
        Argument.assertNotNull(eventChannelName, "eventChannelName");
        Argument.assertNotNull(predicate, "predicate");
        // CheckArgument.checkNull(codec, "codec");

        this.eventChannelName = eventChannelName.trim();
        this.predicate = predicate;
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
     * Returns the predicate used to validate values for this event channel.
     *
     * @return the validation predicate.
     */
    public final Predicate<Object> getPredicate() {
        return predicate;
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
    @Override
    public int hashCode() {
        return this.eventChannelName.hashCode();
    }

    /**
     * Returns whether this event channel description is equal to another.
     *
     * <p>Two event channel descriptions are equal when they have the same name
     * and their validation predicates are equal. Predicate implementations used
     * by event channel descriptions should implement value-based
     * {@code equals()} and {@code hashCode()} when independently created
     * predicates can represent equivalent validation rules.</p>
     *
     * @param obj the object to compare with.
     * @return {@code true} if the descriptions are equal.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof EventChannelDescription)) {
            return false;
        }

        EventChannelDescription ecd = (EventChannelDescription) object;

        return ecd.eventChannelName.equals(this.eventChannelName)
                && ecd.predicate.equals(this.predicate)
                && ecd.fireOnConnect == this.fireOnConnect
                && ecd.rollbackParticipant == this.rollbackParticipant;
    }

    /**
     * Returns a string representation of the event channel description.
     */
    @Override
    public String toString() {
        return super.toString() + "[eventChannelName = " + this.eventChannelName + "]";
    }
}
