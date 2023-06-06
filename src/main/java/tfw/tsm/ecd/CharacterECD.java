package tfw.tsm.ecd;

import tfw.value.ClassValueConstraint;

/**
 * A <code>java.lang.Character</code> event channel descritpion
 */
public class CharacterECD extends ObjectECD {
    /**
     * Creates an event channel description with the specified name.
     * @param name the name of the event channel.
     */
    public CharacterECD(String name) {
        super(name, ClassValueConstraint.CHARACTER);
    }
}
