package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class FloatIlaFactoryEcd extends ObjectECD {
    public FloatIlaFactoryEcd(String name) {
        super(name, ClassValueConstraint.getInstance(FloatIlaFactory.class));
    }
}
