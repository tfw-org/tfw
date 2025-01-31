package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class ByteIlaFactoryEcd extends ObjectECD {
    public ByteIlaFactoryEcd(String name) {
        super(name, ClassValueConstraint.getInstance(ByteIlaFactory.class));
    }
}
