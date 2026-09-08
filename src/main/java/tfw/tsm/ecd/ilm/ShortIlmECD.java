package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.shortilm.ShortIlm;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class ShortIlmECD extends ObjectECD {
    public ShortIlmECD(String name) {
        super(name, new IsAssignableFromPredicate(ShortIlm.class));
    }
}
