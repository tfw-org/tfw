package tfw.tsm.ecd;

/**
 * A <code>java.lang.Integer</code> event channel descritpion
 */
public class ByteECD extends ObjectECD {
    /**
     * Creates an event channel description with the specified name.
     * @param name the name of the event channel.
     */
    public ByteECD(String name) {
        super(name, new IsAssignableFromPredicate(Byte.class));
    }
}
