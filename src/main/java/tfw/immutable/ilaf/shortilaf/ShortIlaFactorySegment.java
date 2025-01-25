package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public class ShortIlaFactorySegment {
    private ShortIlaFactorySegment() {}

    public static ShortIlaFactory create(ShortIlaFactory factory, final long start, final long length) {
        return new ShortIlaFactoryImpl(factory, start, length);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final ShortIlaFactory factory;
        private final long start;
        private final long length;

        public ShortIlaFactoryImpl(final ShortIlaFactory factory, final long start, final long length) {
            Argument.assertNotNull(factory, "factory");

            this.factory = factory;
            this.start = start;
            this.length = length;
        }

        @Override
        public ShortIla create() {
            return ShortIlaSegment.create(factory.create(), start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
