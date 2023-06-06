package tfw.tsm.ecd.ila;

import tfw.immutable.ila.intila.IntIla;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class IntIlaECD extends ObjectECD {
    public IntIlaECD(String name) {
        super(name, ClassValueConstraint.getInstance(IntIla.class));
    }
}
