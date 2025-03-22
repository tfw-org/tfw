package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaFromCastDoubleIla;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;

public class ByteIlaFactoryFromCastDoubleIlaFactory {
    private ByteIlaFactoryFromCastDoubleIlaFactory() {}

    public static ByteIlaFactory create(DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
        Argument.assertNotNull(doubleIlaFactory, "doubleIlaFactory");

        return () -> ByteIlaFromCastDoubleIla.create(doubleIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
