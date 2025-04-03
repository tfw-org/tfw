package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class ShortIlaFactoryEcd extends ObjectECD {
    public ShortIlaFactoryEcd(String name) {
        super(name, ClassValueConstraint.getInstance(ShortIlaFactory.class));
    }
}
