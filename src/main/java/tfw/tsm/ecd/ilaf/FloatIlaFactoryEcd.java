package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class FloatIlaFactoryEcd extends ObjectECD {
    public FloatIlaFactoryEcd(String name) {
        super(name, new IsAssignableFromPredicate(FloatIlaFactory.class));
    }
}
