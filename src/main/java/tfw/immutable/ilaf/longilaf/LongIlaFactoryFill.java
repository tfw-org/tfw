package tfw.immutable.ilaf.longilaf;

import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFill;

public class LongIlaFactoryFill {
    private LongIlaFactoryFill() {}

    public static LongIlaFactory create(final long value, final long length) {
        return new LongIlaFactoryImpl(value, length);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final long value;
        private final long length;

        public LongIlaFactoryImpl(final long value, final long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        public LongIla create() {
            return LongIlaFill.create(value, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
