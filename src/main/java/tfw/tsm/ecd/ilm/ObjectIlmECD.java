package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.objectilm.ObjectIlm;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class ObjectIlmECD extends ObjectECD {
    public ObjectIlmECD(String name) {
        super(name, new IsAssignableFromPredicate(ObjectIlm.class));
    }
}
