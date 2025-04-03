package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaInsert;

public class ByteIlaFactoryInsert {
    private ByteIlaFactoryInsert() {}

    public static ByteIlaFactory create(ByteIlaFactory ilaFactory, long index, byte value) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> ByteIlaInsert.create(ilaFactory.create(), index, value);
    }
}
// AUTO GENERATED FROM TEMPLATE
