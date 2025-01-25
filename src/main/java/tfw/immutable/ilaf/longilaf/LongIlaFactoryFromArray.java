package tfw.immutable.ilaf.longilaf;

import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

public class LongIlaFactoryFromArray {
    private LongIlaFactoryFromArray() {}

    public static LongIlaFactory create(final long[] array) {
        return new LongIlaFactoryImpl(array);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final long[] array;

        public LongIlaFactoryImpl(final long[] array) {
            this.array = array;
        }

        @Override
        public LongIla create() {
            return LongIlaFromArray.create(array);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
