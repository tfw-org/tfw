package tfw.tsm.ecd;

/**
 * A <code>java.lang.Long</code> event channel description
 */
public class LongECD extends ObjectECD {
    /**
     * Creates an event channel description with the specified name.
     * @param name the name of the event channel.
     */
    public LongECD(String name) {
        super(name, new IsAssignableFromPredicate(Long.class));
    }
}
