package tfw.immutable.ilaf.charilaf;

import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;

public class CharIlaFactoryFromArray {
    private CharIlaFactoryFromArray() {}

    public static CharIlaFactory create(final char[] array) {
        return new CharIlaFactoryImpl(array);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final char[] array;

        public CharIlaFactoryImpl(final char[] array) {
            this.array = array;
        }

        @Override
        public CharIla create() {
            return CharIlaFromArray.create(array);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
