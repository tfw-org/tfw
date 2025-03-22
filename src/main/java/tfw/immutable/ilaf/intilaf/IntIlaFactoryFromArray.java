package tfw.immutable.ilaf.intilaf;

import tfw.immutable.ila.intila.IntIlaFromArray;

public class IntIlaFactoryFromArray {
    private IntIlaFactoryFromArray() {}

    public static IntIlaFactory create(final int[] array) {
        return () -> IntIlaFromArray.create(array);
    }
}
// AUTO GENERATED FROM TEMPLATE
