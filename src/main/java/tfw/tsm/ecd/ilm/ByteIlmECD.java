package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.byteilm.ByteIlm;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class ByteIlmECD extends ObjectECD {
    public ByteIlmECD(String name) {
        super(name, new IsAssignableFromPredicate(ByteIlm.class));
    }
}
