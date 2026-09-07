package tfw.tsm.ecd;

import java.util.Set;

public class SetECD extends ObjectECD {
    public SetECD(String name) {
        super(name, new IsAssignableFromPredicate(Set.class));
    }
}
