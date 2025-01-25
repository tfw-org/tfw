package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaConcatenate;

public class CharIlaFactoryConcatenate {
    private CharIlaFactoryConcatenate() {}

    public static CharIlaFactory create(CharIlaFactory leftFactory, CharIlaFactory rightFactory) {
        return new CharIlaFactoryImpl(leftFactory, rightFactory);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final CharIlaFactory leftFactory;
        private final CharIlaFactory rightFactory;

        public CharIlaFactoryImpl(final CharIlaFactory leftFactory, CharIlaFactory rightFactory) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
        }

        @Override
        public CharIla create() {
            return CharIlaConcatenate.create(leftFactory.create(), rightFactory.create());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
