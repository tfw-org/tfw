package tfw.tsm.ecd;

/**
 * A <code>java.lang.Float</code> event channel descritpion.
 */
public class FloatECD extends ObjectECD {
    /**
     * Creates an event channel description with the specified name.
     * @param name the name of the event channel.
     */
    public FloatECD(String name) {
        super(name, new IsAssignableFromPredicate(Float.class));
    }
}
