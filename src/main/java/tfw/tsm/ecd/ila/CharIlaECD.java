package tfw.tsm.ecd.ila;

import tfw.immutable.ila.charila.CharIla;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class CharIlaECD extends ObjectECD {
    public CharIlaECD(String name) {
        super(name, new IsAssignableFromPredicate(CharIla.class));
    }
}
