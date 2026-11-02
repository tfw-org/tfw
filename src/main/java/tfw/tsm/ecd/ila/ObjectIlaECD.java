package tfw.tsm.ecd.ila;

import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class ObjectIlaECD extends ObjectECD {
    public ObjectIlaECD(String name) {
        super(name, new IsAssignableFromPredicate(ObjectIla.class));
    }
}
