package tfw.tsm.ecd;

import tfw.value.ClassValueConstraint;
import tfw.value.FloatConstraint;

/**
 * A <code>java.lang.Float</code> event channel descritpion.
 */
public class FloatECD extends ObjectECD {

    /**
     * Creates an event channel description with the specified attribute.
     * @param name the name of the event channel.
     * @param min the minimum value allowed.
     * @param max the maximum value allowed.
     * @param minInclusive if true <code>min</code> is a valid value,
     * otherwise it is not valid.
     * @param maxInclusive if true <code>max</code> is a valid value,
     * otherwise it is not valid.
     */
    public FloatECD(String name, float min, float max, boolean minInclusive, boolean maxInclusive) {
        super(name, new FloatConstraint(min, max, minInclusive, maxInclusive));
    }

    /**
     * Creates an event channel description with the specified name.
     * @param name the name of the event channel.
     */
    public FloatECD(String name) {
        super(name, ClassValueConstraint.FLOAT);
    }
}
