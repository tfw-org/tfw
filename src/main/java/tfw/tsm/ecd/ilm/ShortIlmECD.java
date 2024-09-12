package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.shortilm.ShortIlm;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class ShortIlmECD extends ObjectECD {
    public ShortIlmECD(String name) {
        super(name, ClassValueConstraint.getInstance(ShortIlm.class));
    }
}
