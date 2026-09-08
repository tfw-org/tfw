package tfw.tsm.ecd.ila;

import tfw.immutable.ila.shortila.ShortIla;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class ShortIlaECD extends ObjectECD {
    public ShortIlaECD(String name) {
        super(name, new IsAssignableFromPredicate(ShortIla.class));
    }
}
