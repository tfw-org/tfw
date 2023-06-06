package tfw.awt.ecd;

import java.awt.image.ColorModel;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class ColorModelECD extends ObjectECD {
    public ColorModelECD(String name) {
        super(name, ClassValueConstraint.getInstance(ColorModel.class));
    }
}
