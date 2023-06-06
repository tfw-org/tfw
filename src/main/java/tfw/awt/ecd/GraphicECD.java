package tfw.awt.ecd;

import tfw.awt.graphic.Graphic;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class GraphicECD extends ObjectECD {
    public GraphicECD(String name) {
        super(name, ClassValueConstraint.getInstance(Graphic.class));
    }
}
