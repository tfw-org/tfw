package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.charilm.CharIlm;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class CharIlmECD extends ObjectECD {
    public CharIlmECD(String name) {
        super(name, new IsAssignableFromPredicate(CharIlm.class));
    }
}
