package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class DoubleIlaFactoryEcd extends ObjectECD {
    public DoubleIlaFactoryEcd(String name) {
        super(name, new IsAssignableFromPredicate(DoubleIlaFactory.class));
    }
}
