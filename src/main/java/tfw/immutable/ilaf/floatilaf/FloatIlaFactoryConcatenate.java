package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaConcatenate;

public class FloatIlaFactoryConcatenate {
    private FloatIlaFactoryConcatenate() {}

    public static FloatIlaFactory create(FloatIlaFactory leftFactory, FloatIlaFactory rightFactory) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> FloatIlaConcatenate.create(leftFactory.create(), rightFactory.create());
    }
}
// AUTO GENERATED FROM TEMPLATE
