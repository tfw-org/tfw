package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.booleanilm.BooleanIlm;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class BooleanIlmECD extends ObjectECD {
    public BooleanIlmECD(String name) {
        super(name, ClassValueConstraint.getInstance(BooleanIlm.class));
    }
}
