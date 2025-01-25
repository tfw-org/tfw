package tfw.immutable.ilaf.booleanilaf;

import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFill;

public class BooleanIlaFactoryFill {
    private BooleanIlaFactoryFill() {}

    public static BooleanIlaFactory create(final boolean value, final long length) {
        return new BooleanIlaFactoryImpl(value, length);
    }

    private static class BooleanIlaFactoryImpl implements BooleanIlaFactory {
        private final boolean value;
        private final long length;

        public BooleanIlaFactoryImpl(final boolean value, final long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        public BooleanIla create() {
            return BooleanIlaFill.create(value, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
