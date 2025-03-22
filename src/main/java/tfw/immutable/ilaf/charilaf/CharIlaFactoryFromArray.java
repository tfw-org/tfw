package tfw.immutable.ilaf.charilaf;

import tfw.immutable.ila.charila.CharIlaFromArray;

public class CharIlaFactoryFromArray {
    private CharIlaFactoryFromArray() {}

    public static CharIlaFactory create(final char[] array) {
        return () -> CharIlaFromArray.create(array);
    }
}
// AUTO GENERATED FROM TEMPLATE
