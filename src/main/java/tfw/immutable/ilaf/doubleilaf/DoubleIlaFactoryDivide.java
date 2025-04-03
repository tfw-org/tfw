package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaDivide;

public class DoubleIlaFactoryDivide {
    private DoubleIlaFactoryDivide() {}

    public static DoubleIlaFactory create(
            final DoubleIlaFactory leftFactory, final DoubleIlaFactory rightFactory, final int bufferSize) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> DoubleIlaDivide.create(leftFactory.create(), rightFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
