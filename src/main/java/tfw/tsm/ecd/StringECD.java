package tfw.tsm.ecd;

/**
 * A <code>java.lang.String</code> event channel description
 */
public class StringECD extends ObjectECD {
    /**
     * Creates an event channel description with the specified name.
     *
     * @param name
     *            the name of the event channel.
     */
    public StringECD(String name) {
        super(name, new IsAssignableFromPredicate(String.class));
    }
}
