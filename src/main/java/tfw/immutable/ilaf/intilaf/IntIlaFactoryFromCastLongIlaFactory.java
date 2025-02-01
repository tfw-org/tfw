package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromCastLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class IntIlaFactoryFromCastLongIlaFactory {
    private IntIlaFactoryFromCastLongIlaFactory() {}

    public static IntIlaFactory create(LongIlaFactory longIlaFactory, final int bufferSize) {
        return new IntIlaFactoryImpl(longIlaFactory, bufferSize);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final LongIlaFactory longIlaFactory;
        private final int bufferSize;

        public IntIlaFactoryImpl(final LongIlaFactory longIlaFactory, final int bufferSize) {
            Argument.assertNotNull(longIlaFactory, "longIlaFactory");

            this.longIlaFactory = longIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public IntIla create() {
            return IntIlaFromCastLongIla.create(longIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
