package tfw.awt.ecd;

import java.awt.image.ImageObserver;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class ImageObserverECD extends ObjectECD {
    public ImageObserverECD(String name) {
        super(name, new IsAssignableFromPredicate(ImageObserver.class));
    }
}
