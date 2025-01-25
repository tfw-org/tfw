package tfw.immutable.ilaf.charilaf;

import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFill;

public class CharIlaFactoryFill {
    private CharIlaFactoryFill() {}

    public static CharIlaFactory create(final char value, final long length) {
        return new CharIlaFactoryImpl(value, length);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final char value;
        private final long length;

        public CharIlaFactoryImpl(final char value, final long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        public CharIla create() {
            return CharIlaFill.create(value, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
