package tfw.swing.ecd;

import javax.swing.Icon;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class IconECD extends ObjectECD {
    public IconECD(String name) {
        super(name, new IsAssignableFromPredicate(Icon.class));
    }
}
