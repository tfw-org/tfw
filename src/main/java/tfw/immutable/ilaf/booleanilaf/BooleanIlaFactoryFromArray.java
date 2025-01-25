package tfw.immutable.ilaf.booleanilaf;

import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;

public class BooleanIlaFactoryFromArray {
    private BooleanIlaFactoryFromArray() {}

    public static BooleanIlaFactory create(final boolean[] array) {
        return new BooleanIlaFactoryImpl(array);
    }

    private static class BooleanIlaFactoryImpl implements BooleanIlaFactory {
        private final boolean[] array;

        public BooleanIlaFactoryImpl(final boolean[] array) {
            this.array = array;
        }

        @Override
        public BooleanIla create() {
            return BooleanIlaFromArray.create(array);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
