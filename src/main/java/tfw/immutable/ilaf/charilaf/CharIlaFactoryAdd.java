package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaAdd;

public class CharIlaFactoryAdd {
    private CharIlaFactoryAdd() {}

    public static CharIlaFactory create(CharIlaFactory leftFactory, CharIlaFactory rightFactory, int bufferSize) {
        return new CharIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final CharIlaFactory leftFactory;
        private final CharIlaFactory rightFactory;
        private final int bufferSize;

        public CharIlaFactoryImpl(CharIlaFactory leftFactory, CharIlaFactory rightFactory, int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public CharIla create() {
            return CharIlaAdd.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
