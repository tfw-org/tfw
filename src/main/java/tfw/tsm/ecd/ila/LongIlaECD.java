package tfw.tsm.ecd.ila;

import tfw.immutable.ila.longila.LongIla;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class LongIlaECD extends ObjectECD {
    public LongIlaECD(String name) {
        super(name, ClassValueConstraint.getInstance(LongIla.class));
    }
}
