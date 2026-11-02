package tfw.tsm.ecd;

/**
 * A <code>java.lang.Boolean</code> event channel descritpion
 */
public class BooleanECD extends ObjectECD {
    /**
     * Creates an event channel description with the specified name.
     * @param name the name of the event channel.
     */
    public BooleanECD(String name) {
        super(name, new IsAssignableFromPredicate(Boolean.class));
    }
}
