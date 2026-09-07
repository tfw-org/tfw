package tfw.tsm.ecd.ila;

import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class DoubleIlaECD extends ObjectECD {
    public DoubleIlaECD(String name) {
        super(name, new IsAssignableFromPredicate(DoubleIla.class));
    }
}
