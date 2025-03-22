package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaFromCastShortIla;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;

public class ByteIlaFactoryFromCastShortIlaFactory {
    private ByteIlaFactoryFromCastShortIlaFactory() {}

    public static ByteIlaFactory create(ShortIlaFactory shortIlaFactory, final int bufferSize) {
        Argument.assertNotNull(shortIlaFactory, "shortIlaFactory");

        return () -> ByteIlaFromCastShortIla.create(shortIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
