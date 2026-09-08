package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class DoubleIlmECD extends ObjectECD {
    public DoubleIlmECD(String name) {
        super(name, new IsAssignableFromPredicate(DoubleIlm.class));
    }
}
