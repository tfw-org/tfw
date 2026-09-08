package tfw.awt.ecd;

import tfw.awt.graphic.Graphic;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class GraphicECD extends ObjectECD {
    public GraphicECD(String name) {
        super(name, new IsAssignableFromPredicate(Graphic.class));
    }
}
