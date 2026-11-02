package tfw.awt.ecd;

import java.awt.image.ColorModel;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class ColorModelECD extends ObjectECD {
    public ColorModelECD(String name) {
        super(name, new IsAssignableFromPredicate(ColorModel.class));
    }
}
