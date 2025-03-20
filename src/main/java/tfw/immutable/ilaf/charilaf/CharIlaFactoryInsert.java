package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaInsert;

public class CharIlaFactoryInsert {
    private CharIlaFactoryInsert() {}

    public static CharIlaFactory create(CharIlaFactory ilaFactory, long index, char value) {
        return new CharIlaFactoryImpl(ilaFactory, index, value);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final CharIlaFactory ilaFactory;
        private final long index;
        private final char value;

        public CharIlaFactoryImpl(final CharIlaFactory ilaFactory, long index, char value) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.index = index;
            this.value = value;
        }

        @Override
        public CharIla create() {
            return CharIlaInsert.create(ilaFactory.create(), index, value);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
