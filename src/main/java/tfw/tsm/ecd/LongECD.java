package tfw.tsm.ecd;

import tfw.value.ClassValueConstraint;
import tfw.value.LongConstraint;

/**
 * A <code>java.lang.Long</code> event channel description
 */
public class LongECD extends ObjectECD {
    /**
     * Creates an event channel description with the specified name.
     * @param name the name of the event channel.
     * @param min the minimum value allowed.
     * @param max the maximum value allowed.
     */
    public LongECD(String name, long min, long max) {
        super(name, new LongConstraint(min, max));
    }

    /**
     * Creates an event channel description with the specified name.
     * @param name the name of the event channel.
     */
    public LongECD(String name) {
        super(name, ClassValueConstraint.LONG);
    }
}
