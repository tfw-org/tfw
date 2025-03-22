package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaFromCastLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class ByteIlaFactoryFromCastLongIlaFactory {
    private ByteIlaFactoryFromCastLongIlaFactory() {}

    public static ByteIlaFactory create(LongIlaFactory longIlaFactory, final int bufferSize) {
        Argument.assertNotNull(longIlaFactory, "longIlaFactory");

        return () -> ByteIlaFromCastLongIla.create(longIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
