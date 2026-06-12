package tfw.tsm.ecd;

import java.util.Map;
import tfw.value.ClassValueConstraint;

public class MapECD extends ObjectECD {
    public MapECD(String name) {
        super(name, ClassValueConstraint.getInstance(Map.class));
    }
}
