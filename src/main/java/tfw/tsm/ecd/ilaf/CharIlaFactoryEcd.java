package tfw.tsm.ecd.ilaf;

import tfw.immutable.ilaf.charilaf.CharIlaFactory;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class CharIlaFactoryEcd extends ObjectECD {
    public CharIlaFactoryEcd(String name) {
        super(name, new IsAssignableFromPredicate(CharIlaFactory.class));
    }
}
