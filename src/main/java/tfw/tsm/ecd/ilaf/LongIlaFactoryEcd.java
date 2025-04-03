package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.longilaf.LongIlaFactory;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class LongIlaFactoryEcd extends ObjectECD {
    public LongIlaFactoryEcd(String name) {
        super(name, ClassValueConstraint.getInstance(LongIlaFactory.class));
    }
}
