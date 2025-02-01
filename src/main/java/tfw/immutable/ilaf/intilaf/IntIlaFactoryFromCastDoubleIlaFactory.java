package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromCastDoubleIla;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;

public class IntIlaFactoryFromCastDoubleIlaFactory {
    private IntIlaFactoryFromCastDoubleIlaFactory() {}

    public static IntIlaFactory create(DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
        return new IntIlaFactoryImpl(doubleIlaFactory, bufferSize);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final DoubleIlaFactory doubleIlaFactory;
        private final int bufferSize;

        public IntIlaFactoryImpl(final DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
            Argument.assertNotNull(doubleIlaFactory, "doubleIlaFactory");

            this.doubleIlaFactory = doubleIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public IntIla create() {
            return IntIlaFromCastDoubleIla.create(doubleIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
