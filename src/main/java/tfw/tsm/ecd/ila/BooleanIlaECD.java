package tfw.tsm.ecd.ila;

import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class BooleanIlaECD extends ObjectECD {
    public BooleanIlaECD(String name) {
        super(name, new IsAssignableFromPredicate(BooleanIla.class));
    }
}
