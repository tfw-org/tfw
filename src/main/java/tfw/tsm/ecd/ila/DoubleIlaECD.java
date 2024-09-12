package tfw.tsm.ecd.ila;

import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class DoubleIlaECD extends ObjectECD {
    public DoubleIlaECD(String name) {
        super(name, ClassValueConstraint.getInstance(DoubleIla.class));
    }
}
