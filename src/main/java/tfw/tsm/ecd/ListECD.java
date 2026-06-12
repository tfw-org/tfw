package tfw.tsm.ecd;

import java.util.List;
import tfw.value.ClassValueConstraint;

public class ListECD extends ObjectECD {
    public ListECD(String name) {
        super(name, ClassValueConstraint.getInstance(List.class));
    }
}
