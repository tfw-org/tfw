package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class DoubleIlmECD extends ObjectECD {
    public DoubleIlmECD(String name) {
        super(name, ClassValueConstraint.getInstance(DoubleIlm.class));
    }
}
