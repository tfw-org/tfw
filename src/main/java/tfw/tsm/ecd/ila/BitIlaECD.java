package tfw.tsm.ecd.ila;

import tfw.immutable.ila.bitila.BitIla;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class BitIlaECD extends ObjectECD {
    public BitIlaECD(String name) {
        super(name, ClassValueConstraint.getInstance(BitIla.class));
    }
}
