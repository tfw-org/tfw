package tfw.immutable.ilaf.booleanilaf;

import tfw.immutable.ila.booleanila.BooleanIlaFromArray;

public class BooleanIlaFactoryFromArray {
    private BooleanIlaFactoryFromArray() {}

    public static BooleanIlaFactory create(final boolean[] array) {
        return () -> BooleanIlaFromArray.create(array);
    }
}
// AUTO GENERATED FROM TEMPLATE
