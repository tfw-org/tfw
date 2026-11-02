package tfw.tsm.ecd.ila;

import tfw.immutable.ila.byteila.ByteIla;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class ByteIlaECD extends ObjectECD {
    public ByteIlaECD(String name) {
        super(name, new IsAssignableFromPredicate(ByteIla.class));
    }
}
