package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaFromCastCharIla;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;

public class ByteIlaFactoryFromCastCharIlaFactory {
    private ByteIlaFactoryFromCastCharIlaFactory() {}

    public static ByteIlaFactory create(CharIlaFactory charIlaFactory, final int bufferSize) {
        Argument.assertNotNull(charIlaFactory, "charIlaFactory");

        return () -> ByteIlaFromCastCharIla.create(charIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
