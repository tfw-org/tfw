package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromCastCharIla;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;

public class IntIlaFactoryFromCastCharIlaFactory {
    private IntIlaFactoryFromCastCharIlaFactory() {}

    public static IntIlaFactory create(CharIlaFactory charIlaFactory, final int bufferSize) {
        return new IntIlaFactoryImpl(charIlaFactory, bufferSize);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final CharIlaFactory charIlaFactory;
        private final int bufferSize;

        public IntIlaFactoryImpl(final CharIlaFactory charIlaFactory, final int bufferSize) {
            Argument.assertNotNull(charIlaFactory, "charIlaFactory");

            this.charIlaFactory = charIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public IntIla create() {
            return IntIlaFromCastCharIla.create(charIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
