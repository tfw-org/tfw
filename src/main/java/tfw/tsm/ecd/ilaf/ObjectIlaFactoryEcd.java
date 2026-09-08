package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.objectilaf.ObjectIlaFactory;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class ObjectIlaFactoryEcd extends ObjectECD {
    public ObjectIlaFactoryEcd(String name) {
        super(name, new IsAssignableFromPredicate(ObjectIlaFactory.class));
    }
}
