package tfw.tsm.ecd.ila;

import tfw.immutable.ila.intila.IntIla;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class IntIlaECD extends ObjectECD {
    public IntIlaECD(String name) {
        super(name, new IsAssignableFromPredicate(IntIla.class));
    }
}
