package tfw.tsm.ecd;

import java.util.Map;

public class MapECD extends ObjectECD {
    public MapECD(String name) {
        super(name, new IsAssignableFromPredicate(Map.class));
    }
}
