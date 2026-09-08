package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.longilaf.LongIlaFactory;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class LongIlaFactoryEcd extends ObjectECD {
    public LongIlaFactoryEcd(String name) {
        super(name, new IsAssignableFromPredicate(LongIlaFactory.class));
    }
}
