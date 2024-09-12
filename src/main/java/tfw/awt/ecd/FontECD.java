package tfw.awt.ecd;

import java.awt.Font;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class FontECD extends ObjectECD {
    public FontECD(String name) {
        super(name, ClassValueConstraint.getInstance(Font.class));
    }
}
