package tfw.tsm.ecd;

import tfw.value.ClassValueConstraint;
import tfw.value.ShortConstraint;

/**
 * A <code>java.lang.Short</code> event channel descritpion
 */
public class ShortECD extends ObjectECD {

    /**
     * Creates an event channel description with the specified attribute.
     * @param name the name of the event channel.
     * @param min the minimum value allowed.
     * @param max the maximum value allowed.
     */
    public ShortECD(String name, short min, short max) {
        super(name, new ShortConstraint(min, max));
    }

    /**
     *  Creates an short event channel description with the specified name.
     * @param name The name of the event channel.
     */
    public ShortECD(String name) {
        super(name, ClassValueConstraint.SHORT);
    }
}
