package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaAdd;

public class IntIlaFactoryAdd {
    private IntIlaFactoryAdd() {}

    public static IntIlaFactory create(IntIlaFactory leftFactory, IntIlaFactory rightFactory, int bufferSize) {
        return new IntIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final IntIlaFactory leftFactory;
        private final IntIlaFactory rightFactory;
        private final int bufferSize;

        public IntIlaFactoryImpl(IntIlaFactory leftFactory, IntIlaFactory rightFactory, int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public IntIla create() {
            return IntIlaAdd.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
