package tfw.immutable.ilaf.longilaf;

import tfw.immutable.ila.longila.LongIlaFromArray;

public class LongIlaFactoryFromArray {
    private LongIlaFactoryFromArray() {}

    public static LongIlaFactory create(final long[] array) {
        return () -> LongIlaFromArray.create(array);
    }
}
// AUTO GENERATED FROM TEMPLATE
