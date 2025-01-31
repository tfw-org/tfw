package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class BitIlaFactoryEcd extends ObjectECD {
    public BitIlaFactoryEcd(String name) {
        super(name, ClassValueConstraint.getInstance(BitIlaFactory.class));
    }
}
