package tfw.immutable.ilaf.booleanilaf;

import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaSegment;

public class BooleanIlaFactorySegment {
    private BooleanIlaFactorySegment() {}

    public static BooleanIlaFactory create(BooleanIlaFactory factory, final long start, final long length) {
        return new BooleanIlaFactoryImpl(factory, start, length);
    }

    private static class BooleanIlaFactoryImpl implements BooleanIlaFactory {
        private final BooleanIlaFactory factory;
        private final long start;
        private final long length;

        public BooleanIlaFactoryImpl(final BooleanIlaFactory factory, final long start, final long length) {
            Argument.assertNotNull(factory, "factory");

            this.factory = factory;
            this.start = start;
            this.length = length;
        }

        @Override
        public BooleanIla create() {
            return BooleanIlaSegment.create(factory.create(), start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
