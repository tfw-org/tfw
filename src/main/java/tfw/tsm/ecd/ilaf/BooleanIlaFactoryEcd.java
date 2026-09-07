package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.booleanilaf.BooleanIlaFactory;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class BooleanIlaFactoryEcd extends ObjectECD {
    public BooleanIlaFactoryEcd(String name) {
        super(name, new IsAssignableFromPredicate(BooleanIlaFactory.class));
    }
}
