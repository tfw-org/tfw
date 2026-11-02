package tfw.tsm.ecd.ila;

import tfw.immutable.ila.bitila.BitIla;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class BitIlaECD extends ObjectECD {
    public BitIlaECD(String name) {
        super(name, new IsAssignableFromPredicate(BitIla.class));
    }
}
