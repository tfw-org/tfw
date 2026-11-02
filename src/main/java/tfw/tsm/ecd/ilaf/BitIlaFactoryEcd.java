package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class BitIlaFactoryEcd extends ObjectECD {
    public BitIlaFactoryEcd(String name) {
        super(name, new IsAssignableFromPredicate(BitIlaFactory.class));
    }
}
