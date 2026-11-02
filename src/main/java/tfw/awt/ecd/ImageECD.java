package tfw.awt.ecd;

import java.awt.Image;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class ImageECD extends ObjectECD {
    public ImageECD(String name) {
        super(name, new IsAssignableFromPredicate(Image.class));
    }
}
