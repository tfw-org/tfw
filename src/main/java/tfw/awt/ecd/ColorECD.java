package tfw.awt.ecd;

import java.awt.Color;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

/**
 * A <code>java.awt.Color</code> event channel descritpion
 */
public class ColorECD extends ObjectECD {
    /**
     * Creates an event channel description with the specified name.
     * @param name the name of the event channel.
     */
    public ColorECD(String name) {
        super(name, ClassValueConstraint.getInstance(Color.class));
    }
}
