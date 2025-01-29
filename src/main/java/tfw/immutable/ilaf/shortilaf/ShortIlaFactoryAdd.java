package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaAdd;

public class ShortIlaFactoryAdd {
    private ShortIlaFactoryAdd() {}

    public static ShortIlaFactory create(ShortIlaFactory leftFactory, ShortIlaFactory rightFactory, int bufferSize) {
        return new ShortIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final ShortIlaFactory leftFactory;
        private final ShortIlaFactory rightFactory;
        private final int bufferSize;

        public ShortIlaFactoryImpl(ShortIlaFactory leftFactory, ShortIlaFactory rightFactory, int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ShortIla create() {
            return ShortIlaAdd.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
