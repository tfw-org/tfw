package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaFromCastByteIla;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;

public class FloatIlaFactoryFromCastByteIlaFactory {
    private FloatIlaFactoryFromCastByteIlaFactory() {}

    public static FloatIlaFactory create(ByteIlaFactory byteIlaFactory, final int bufferSize) {
        Argument.assertNotNull(byteIlaFactory, "byteIlaFactory");

        return () -> FloatIlaFromCastByteIla.create(byteIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
