package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaDivide;

public class FloatIlaFactoryDivide {
    private FloatIlaFactoryDivide() {}

    public static FloatIlaFactory create(
            final FloatIlaFactory leftFactory, final FloatIlaFactory rightFactory, final int bufferSize) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> FloatIlaDivide.create(leftFactory.create(), rightFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
