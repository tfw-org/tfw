package tfw.awt.ecd;

import java.awt.Font;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class FontECD extends ObjectECD {
    public FontECD(String name) {
        super(name, new IsAssignableFromPredicate(Font.class));
    }
}
