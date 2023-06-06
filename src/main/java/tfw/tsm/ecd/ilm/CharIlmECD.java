package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.charilm.CharIlm;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class CharIlmECD extends ObjectECD {
    public CharIlmECD(String name) {
        super(name, ClassValueConstraint.getInstance(CharIlm.class));
    }
}
