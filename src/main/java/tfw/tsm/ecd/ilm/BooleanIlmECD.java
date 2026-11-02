package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.booleanilm.BooleanIlm;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class BooleanIlmECD extends ObjectECD {
    public BooleanIlmECD(String name) {
        super(name, new IsAssignableFromPredicate(BooleanIlm.class));
    }
}
