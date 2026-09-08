package tfw.tsm.ecd;

/**
 * A <code>java.lang.Integer</code> event channel descritpion
 */
public class IntegerECD extends ObjectECD {
    /**
     * Creates an event channel description with the specified attribute.
     * @param name the name of the event channel.
     * @param min the minimum value allowed.
     * @param max the maximum value allowed.
     */
    public IntegerECD(String name, int min, int max) {
        super(name, new IntegerRangePredicate(min, max));
    }

    /**
     * Creates an event channel description with the specified name.
     * @param name the name of the event channel.
     */
    public IntegerECD(String name) {
        super(name, new IsAssignableFromPredicate(Integer.class));
    }
}
