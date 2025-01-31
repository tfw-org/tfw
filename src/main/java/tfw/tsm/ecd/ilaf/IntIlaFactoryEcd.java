package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.intilaf.IntIlaFactory;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class IntIlaFactoryEcd extends ObjectECD {
    public IntIlaFactoryEcd(String name) {
        super(name, ClassValueConstraint.getInstance(IntIlaFactory.class));
    }
}
