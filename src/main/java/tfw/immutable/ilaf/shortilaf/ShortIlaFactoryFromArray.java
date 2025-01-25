package tfw.immutable.ilaf.shortilaf;

import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

public class ShortIlaFactoryFromArray {
    private ShortIlaFactoryFromArray() {}

    public static ShortIlaFactory create(final short[] array) {
        return new ShortIlaFactoryImpl(array);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final short[] array;

        public ShortIlaFactoryImpl(final short[] array) {
            this.array = array;
        }

        @Override
        public ShortIla create() {
            return ShortIlaFromArray.create(array);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
