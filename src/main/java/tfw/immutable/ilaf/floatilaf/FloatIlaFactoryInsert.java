package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaInsert;

public class FloatIlaFactoryInsert {
    private FloatIlaFactoryInsert() {}

    public static FloatIlaFactory create(FloatIlaFactory ilaFactory, long index, float value) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> FloatIlaInsert.create(ilaFactory.create(), index, value);
    }
}
// AUTO GENERATED FROM TEMPLATE
