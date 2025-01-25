package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaConcatenate;

public class ShortIlaFactoryConcatenate {
    private ShortIlaFactoryConcatenate() {}

    public static ShortIlaFactory create(ShortIlaFactory leftFactory, ShortIlaFactory rightFactory) {
        return new ShortIlaFactoryImpl(leftFactory, rightFactory);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final ShortIlaFactory leftFactory;
        private final ShortIlaFactory rightFactory;

        public ShortIlaFactoryImpl(final ShortIlaFactory leftFactory, ShortIlaFactory rightFactory) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
        }

        @Override
        public ShortIla create() {
            return ShortIlaConcatenate.create(leftFactory.create(), rightFactory.create());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
