package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.longilm.LongIlm;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class LongIlmECD extends ObjectECD {
    public LongIlmECD(String name) {
        super(name, new IsAssignableFromPredicate(LongIlm.class));
    }
}
