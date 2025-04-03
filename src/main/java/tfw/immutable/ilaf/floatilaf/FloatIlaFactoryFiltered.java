package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaFiltered;
import tfw.immutable.ila.floatila.FloatIlaFiltered.FloatFilter;

public class FloatIlaFactoryFiltered {
    private FloatIlaFactoryFiltered() {}

    public static FloatIlaFactory create(FloatIlaFactory ilaFactory, FloatFilter filter, float[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> FloatIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
