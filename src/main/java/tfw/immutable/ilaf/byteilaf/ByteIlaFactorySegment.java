package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public class ByteIlaFactorySegment {
    private ByteIlaFactorySegment() {}

    public static ByteIlaFactory create(ByteIlaFactory factory, final long start, final long length) {
        Argument.assertNotNull(factory, "factory");

        return () -> ByteIlaSegment.create(factory.create(), start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
