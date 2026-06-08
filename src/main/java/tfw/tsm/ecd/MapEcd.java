package tfw.tsm.ecd;

import java.util.Map;
import tfw.value.ClassValueConstraint;

public class MapEcd extends ObjectECD {
    public MapEcd(String name) {
        super(name, ClassValueConstraint.getInstance(Map.class));
    }
}
