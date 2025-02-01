package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaDivide;

public class CharIlaFactoryDivide {
    private CharIlaFactoryDivide() {}

    public static CharIlaFactory create(
            final CharIlaFactory leftFactory, final CharIlaFactory rightFactory, final int bufferSize) {
        return new CharIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final CharIlaFactory leftFactory;
        private final CharIlaFactory rightFactory;
        private final int bufferSize;

        public CharIlaFactoryImpl(
                final CharIlaFactory leftFactory, final CharIlaFactory rightFactory, final int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public CharIla create() {
            return CharIlaDivide.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
