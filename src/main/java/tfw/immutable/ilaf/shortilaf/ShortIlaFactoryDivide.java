package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaDivide;

public class ShortIlaFactoryDivide {
    private ShortIlaFactoryDivide() {}

    public static ShortIlaFactory create(
            final ShortIlaFactory leftFactory, final ShortIlaFactory rightFactory, final int bufferSize) {
        return new ShortIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final ShortIlaFactory leftFactory;
        private final ShortIlaFactory rightFactory;
        private final int bufferSize;

        public ShortIlaFactoryImpl(
                final ShortIlaFactory leftFactory, final ShortIlaFactory rightFactory, final int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ShortIla create() {
            return ShortIlaDivide.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
