package tfw.immutable.ilaf.byteilaf;

import tfw.immutable.ila.byteila.ByteIlaFromArray;

public class ByteIlaFactoryFromArray {
    private ByteIlaFactoryFromArray() {}

    public static ByteIlaFactory create(final byte[] array) {
        return () -> ByteIlaFromArray.create(array);
    }
}
// AUTO GENERATED FROM TEMPLATE
