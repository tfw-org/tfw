package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaFromCastByteIla;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;

public class CharIlaFactoryFromCastByteIlaFactory {
    private CharIlaFactoryFromCastByteIlaFactory() {}

    public static CharIlaFactory create(ByteIlaFactory byteIlaFactory, final int bufferSize) {
        Argument.assertNotNull(byteIlaFactory, "byteIlaFactory");

        return () -> CharIlaFromCastByteIla.create(byteIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
