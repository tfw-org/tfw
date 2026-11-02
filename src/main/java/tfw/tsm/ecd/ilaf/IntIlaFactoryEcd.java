package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.intilaf.IntIlaFactory;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class IntIlaFactoryEcd extends ObjectECD {
    public IntIlaFactoryEcd(String name) {
        super(name, new IsAssignableFromPredicate(IntIlaFactory.class));
    }
}
