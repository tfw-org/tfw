package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.intilm.IntIlm;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class IntIlmECD extends ObjectECD {
    public IntIlmECD(String name) {
        super(name, new IsAssignableFromPredicate(IntIlm.class));
    }
}
