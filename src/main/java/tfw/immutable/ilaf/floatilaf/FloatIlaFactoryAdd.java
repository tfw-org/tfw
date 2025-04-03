package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaAdd;

public class FloatIlaFactoryAdd {
    private FloatIlaFactoryAdd() {}

    public static FloatIlaFactory create(FloatIlaFactory leftFactory, FloatIlaFactory rightFactory, int bufferSize) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> FloatIlaAdd.create(leftFactory.create(), rightFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
