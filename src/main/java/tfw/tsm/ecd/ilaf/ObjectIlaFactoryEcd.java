package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.objectilaf.ObjectIlaFactory;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class ObjectIlaFactoryEcd extends ObjectECD {
    public ObjectIlaFactoryEcd(String name) {
        super(name, ClassValueConstraint.getInstance(ObjectIlaFactory.class));
    }
}
