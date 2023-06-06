package tfw.tsm.ecd.ila;

import tfw.immutable.ila.shortila.ShortIla;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class ShortIlaECD extends ObjectECD {
    public ShortIlaECD(String name) {
        super(name, ClassValueConstraint.getInstance(ShortIla.class));
    }
}
