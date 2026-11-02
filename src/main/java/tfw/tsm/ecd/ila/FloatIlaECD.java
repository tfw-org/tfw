package tfw.tsm.ecd.ila;

import tfw.immutable.ila.floatila.FloatIla;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class FloatIlaECD extends ObjectECD {
    public FloatIlaECD(String name) {
        super(name, new IsAssignableFromPredicate(FloatIla.class));
    }
}
