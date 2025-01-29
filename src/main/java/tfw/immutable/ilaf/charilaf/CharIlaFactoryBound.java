package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaBound;

public class CharIlaFactoryBound {
    private CharIlaFactoryBound() {}

    public static CharIlaFactory create(CharIlaFactory ilaFactory, char minimum, char maximum) {
        return new CharIlaFactoryImpl(ilaFactory, minimum, maximum);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final CharIlaFactory ilaFactory;
        private final char minimum;
        private final char maximum;

        public CharIlaFactoryImpl(final CharIlaFactory ilaFactory, char minimum, char maximum) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        public CharIla create() {
            return CharIlaBound.create(ilaFactory.create(), minimum, maximum);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
