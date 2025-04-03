package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIlaFromCastByteIla;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;

public class IntIlaFactoryFromCastByteIlaFactory {
    private IntIlaFactoryFromCastByteIlaFactory() {}

    public static IntIlaFactory create(ByteIlaFactory byteIlaFactory, final int bufferSize) {
        Argument.assertNotNull(byteIlaFactory, "byteIlaFactory");

        return () -> IntIlaFromCastByteIla.create(byteIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
