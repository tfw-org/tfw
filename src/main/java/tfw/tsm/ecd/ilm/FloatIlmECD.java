package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.floatilm.FloatIlm;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class FloatIlmECD extends ObjectECD {
    public FloatIlmECD(String name) {
        super(name, new IsAssignableFromPredicate(FloatIlm.class));
    }
}
