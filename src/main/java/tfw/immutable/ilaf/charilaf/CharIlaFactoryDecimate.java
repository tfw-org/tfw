package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaDecimate;

public class CharIlaFactoryDecimate {
    private CharIlaFactoryDecimate() {}

    public static CharIlaFactory create(CharIlaFactory ilaFactory, long factor, char[] buffer) {
        return new CharIlaFactoryImpl(ilaFactory, factor, buffer);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final CharIlaFactory ilaFactory;
        private final long factor;
        private final char[] buffer;

        public CharIlaFactoryImpl(final CharIlaFactory ilaFactory, long factor, char[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        public CharIla create() {
            return CharIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
