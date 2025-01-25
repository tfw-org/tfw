package tfw.immutable.ilaf.shortilaf;

import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFill;

public class ShortIlaFactoryFill {
    private ShortIlaFactoryFill() {}

    public static ShortIlaFactory create(final short value, final long length) {
        return new ShortIlaFactoryImpl(value, length);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final short value;
        private final long length;

        public ShortIlaFactoryImpl(final short value, final long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        public ShortIla create() {
            return ShortIlaFill.create(value, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
