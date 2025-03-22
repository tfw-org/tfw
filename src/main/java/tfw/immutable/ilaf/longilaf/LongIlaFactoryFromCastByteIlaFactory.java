package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaFromCastByteIla;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;

public class LongIlaFactoryFromCastByteIlaFactory {
    private LongIlaFactoryFromCastByteIlaFactory() {}

    public static LongIlaFactory create(ByteIlaFactory byteIlaFactory, final int bufferSize) {
        Argument.assertNotNull(byteIlaFactory, "byteIlaFactory");

        return () -> LongIlaFromCastByteIla.create(byteIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
