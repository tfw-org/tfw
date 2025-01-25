package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaSegment;

public class IntIlaFactorySegment {
    private IntIlaFactorySegment() {}

    public static IntIlaFactory create(IntIlaFactory factory, final long start, final long length) {
        return new IntIlaFactoryImpl(factory, start, length);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final IntIlaFactory factory;
        private final long start;
        private final long length;

        public IntIlaFactoryImpl(final IntIlaFactory factory, final long start, final long length) {
            Argument.assertNotNull(factory, "factory");

            this.factory = factory;
            this.start = start;
            this.length = length;
        }

        @Override
        public IntIla create() {
            return IntIlaSegment.create(factory.create(), start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
