package tfw.tsm.ecd;

import java.util.function.Predicate;

/**
 * A <code>java.lang.Object</code> event channel descritpion. This is the base
 * class for all stateful event channel descriptions.
 */
public class ObjectECD extends EventChannelDescription {
    /**
     * Creates an event channel description with the specified name.
     *
     * @param name
     *            the name of the event channel.
     */
    public ObjectECD(String name) {
        super(name, new IsAssignableFromPredicate(Object.class));
    }

    /**
     * Creates an event channel description with the specified attributes. This
     * constructor is made available to sub-classing purposes only.
     *
     * @param eventChannelName
     *            the name of the event channel.
     * @param constraint
     *            the value constraint for the event channel.
     */
    protected ObjectECD(String eventChannelName, Predicate<Object> predicate) {
        super(eventChannelName, predicate, true, true);
    }

    /**
     * Creates an event channel description with the specified attributes. This
     * constructor is intentionally package private.
     *
     * @param eventChannelName
     *            the name of the event channel.
     * @param constraint
     *            the value constraint for the evnet channel.
     * @param fireOnConnect
     *            flag indicating whether the event channel fires state when a
     *            new sink is connected.
     * @param rollbackParticipant
     *            flag indicating whether the event channel participates in
     *            transaction rollbacks.
     */
    ObjectECD(
            String eventChannelName, Predicate<Object> predicate, boolean fireOnConnect, boolean rollbackParticipant) {
        super(eventChannelName, predicate, fireOnConnect, rollbackParticipant);
    }
}
