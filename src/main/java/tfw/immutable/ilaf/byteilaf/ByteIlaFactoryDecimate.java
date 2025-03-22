package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaDecimate;

public class ByteIlaFactoryDecimate {
    private ByteIlaFactoryDecimate() {}

    public static ByteIlaFactory create(ByteIlaFactory ilaFactory, long factor, byte[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> ByteIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
