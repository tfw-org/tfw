package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class DoubleIlaFactoryEcd extends ObjectECD {
    public DoubleIlaFactoryEcd(String name) {
        super(name, ClassValueConstraint.getInstance(DoubleIlaFactory.class));
    }
}
