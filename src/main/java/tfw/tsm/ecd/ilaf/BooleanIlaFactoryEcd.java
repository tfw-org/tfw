package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.booleanilaf.BooleanIlaFactory;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class BooleanIlaFactoryEcd extends ObjectECD {
    public BooleanIlaFactoryEcd(String name) {
        super(name, ClassValueConstraint.getInstance(BooleanIlaFactory.class));
    }
}
