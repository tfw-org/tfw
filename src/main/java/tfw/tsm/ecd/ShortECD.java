package tfw.tsm.ecd;

/**
 * A <code>java.lang.Short</code> event channel descritpion
 */
public class ShortECD extends ObjectECD {
    /**
     *  Creates an short event channel description with the specified name.
     * @param name The name of the event channel.
     */
    public ShortECD(String name) {
        super(name, new IsAssignableFromPredicate(Short.class));
    }
}
