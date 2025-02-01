package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromCastCharIla;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;

public class ByteIlaFactoryFromCastCharIlaFactory {
    private ByteIlaFactoryFromCastCharIlaFactory() {}

    public static ByteIlaFactory create(CharIlaFactory charIlaFactory, final int bufferSize) {
        return new ByteIlaFactoryImpl(charIlaFactory, bufferSize);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final CharIlaFactory charIlaFactory;
        private final int bufferSize;

        public ByteIlaFactoryImpl(final CharIlaFactory charIlaFactory, final int bufferSize) {
            Argument.assertNotNull(charIlaFactory, "charIlaFactory");

            this.charIlaFactory = charIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ByteIla create() {
            return ByteIlaFromCastCharIla.create(charIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
