package tfw.awt.ecd;

import java.awt.Graphics;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class GraphicECD extends ObjectECD {
    public GraphicECD(String name) {
        super(name, new IsAssignableFromPredicate(Graphics.class));
    }
}
