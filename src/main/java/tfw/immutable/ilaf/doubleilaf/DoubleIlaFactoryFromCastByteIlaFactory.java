package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastByteIla;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;

public class DoubleIlaFactoryFromCastByteIlaFactory {
    private DoubleIlaFactoryFromCastByteIlaFactory() {}

    public static DoubleIlaFactory create(ByteIlaFactory byteIlaFactory, final int bufferSize) {
        Argument.assertNotNull(byteIlaFactory, "byteIlaFactory");

        return () -> DoubleIlaFromCastByteIla.create(byteIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
