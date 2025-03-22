package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaFromCastIntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class ByteIlaFactoryFromCastIntIlaFactory {
    private ByteIlaFactoryFromCastIntIlaFactory() {}

    public static ByteIlaFactory create(IntIlaFactory intIlaFactory, final int bufferSize) {
        Argument.assertNotNull(intIlaFactory, "intIlaFactory");

        return () -> ByteIlaFromCastIntIla.create(intIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
