package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromCastCharIla;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;

public class FloatIlaFactoryFromCastCharIlaFactory {
    private FloatIlaFactoryFromCastCharIlaFactory() {}

    public static FloatIlaFactory create(CharIlaFactory charIlaFactory, final int bufferSize) {
        return new FloatIlaFactoryImpl(charIlaFactory, bufferSize);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final CharIlaFactory charIlaFactory;
        private final int bufferSize;

        public FloatIlaFactoryImpl(final CharIlaFactory charIlaFactory, final int bufferSize) {
            Argument.assertNotNull(charIlaFactory, "charIlaFactory");

            this.charIlaFactory = charIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public FloatIla create() {
            return FloatIlaFromCastCharIla.create(charIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
