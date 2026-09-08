package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class ByteIlaFactoryEcd extends ObjectECD {
    public ByteIlaFactoryEcd(String name) {
        super(name, new IsAssignableFromPredicate(ByteIlaFactory.class));
    }
}
