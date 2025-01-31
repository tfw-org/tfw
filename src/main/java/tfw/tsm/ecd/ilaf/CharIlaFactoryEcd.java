package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.charilaf.CharIlaFactory;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class CharIlaFactoryEcd extends ObjectECD {
    public CharIlaFactoryEcd(String name) {
        super(name, ClassValueConstraint.getInstance(CharIlaFactory.class));
    }
}
