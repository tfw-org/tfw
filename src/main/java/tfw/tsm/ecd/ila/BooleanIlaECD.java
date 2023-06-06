package tfw.tsm.ecd.ila;

import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class BooleanIlaECD extends ObjectECD {
    public BooleanIlaECD(String name) {
        super(name, ClassValueConstraint.getInstance(BooleanIla.class));
    }
}
