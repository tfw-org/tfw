package tfw.tsm.ecd;

import java.util.List;

public class ListECD extends ObjectECD {
    public ListECD(String name) {
        super(name, new IsAssignableFromPredicate(List.class));
    }
}
