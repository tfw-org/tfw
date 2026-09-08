package tfw.tsm.ecd;

/**
 * A <code>java.lang.Double</code> event channel descritpion.
 */
public class DoubleECD extends ObjectECD {
    /**
     * Creates an event channel description with the specified name.
     * @param name the name of the event channel.
     */
    public DoubleECD(String name) {
        super(name, new IsAssignableFromPredicate(Double.class));
    }
}
