package tfw.immutable.ilaf.shortilaf;

import tfw.immutable.ila.shortila.ShortIlaFromArray;

public class ShortIlaFactoryFromArray {
    private ShortIlaFactoryFromArray() {}

    public static ShortIlaFactory create(final short[] array) {
        return () -> ShortIlaFromArray.create(array);
    }
}
// AUTO GENERATED FROM TEMPLATE
