package tfw.immutable.ilaf.byteilaf;

import tfw.immutable.ila.byteila.ByteIlaFill;

public class ByteIlaFactoryFill {
    private ByteIlaFactoryFill() {}

    public static ByteIlaFactory create(final byte value, final long length) {
        return () -> ByteIlaFill.create(value, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
