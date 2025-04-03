package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaFromCastFloatIla;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;

public class ByteIlaFactoryFromCastFloatIlaFactory {
    private ByteIlaFactoryFromCastFloatIlaFactory() {}

    public static ByteIlaFactory create(FloatIlaFactory floatIlaFactory, final int bufferSize) {
        Argument.assertNotNull(floatIlaFactory, "floatIlaFactory");

        return () -> ByteIlaFromCastFloatIla.create(floatIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
