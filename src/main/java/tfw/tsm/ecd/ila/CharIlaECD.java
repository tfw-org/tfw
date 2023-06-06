package tfw.tsm.ecd.ila;

import tfw.immutable.ila.charila.CharIla;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class CharIlaECD extends ObjectECD {
    public CharIlaECD(String name) {
        super(name, ClassValueConstraint.getInstance(CharIla.class));
    }
}
