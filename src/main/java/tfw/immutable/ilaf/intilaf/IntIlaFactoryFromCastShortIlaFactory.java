package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromCastShortIla;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;

public class IntIlaFactoryFromCastShortIlaFactory {
    private IntIlaFactoryFromCastShortIlaFactory() {}

    public static IntIlaFactory create(ShortIlaFactory shortIlaFactory, final int bufferSize) {
        return new IntIlaFactoryImpl(shortIlaFactory, bufferSize);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final ShortIlaFactory shortIlaFactory;
        private final int bufferSize;

        public IntIlaFactoryImpl(final ShortIlaFactory shortIlaFactory, final int bufferSize) {
            Argument.assertNotNull(shortIlaFactory, "shortIlaFactory");

            this.shortIlaFactory = shortIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public IntIla create() {
            return IntIlaFromCastShortIla.create(shortIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
