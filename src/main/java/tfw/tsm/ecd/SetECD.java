package tfw.tsm.ecd;

import java.util.Set;
import tfw.value.ClassValueConstraint;

public class SetECD extends ObjectECD {
    public SetECD(String name) {
        super(name, ClassValueConstraint.getInstance(Set.class));
    }
}
