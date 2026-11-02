package tfw.tsm.ecd.ila;

import tfw.immutable.ila.longila.LongIla;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class LongIlaECD extends ObjectECD {
    public LongIlaECD(String name) {
        super(name, new IsAssignableFromPredicate(LongIla.class));
    }
}
