package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.byteilm.ByteIlm;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class ByteIlmECD extends ObjectECD {
    public ByteIlmECD(String name) {
        super(name, ClassValueConstraint.getInstance(ByteIlm.class));
    }
}
