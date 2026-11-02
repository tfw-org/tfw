package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class ShortIlaFactoryEcd extends ObjectECD {
    public ShortIlaFactoryEcd(String name) {
        super(name, new IsAssignableFromPredicate(ShortIlaFactory.class));
    }
}
