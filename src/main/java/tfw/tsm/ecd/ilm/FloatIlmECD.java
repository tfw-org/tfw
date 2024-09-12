package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.floatilm.FloatIlm;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class FloatIlmECD extends ObjectECD {
    public FloatIlmECD(String name) {
        super(name, ClassValueConstraint.getInstance(FloatIlm.class));
    }
}
