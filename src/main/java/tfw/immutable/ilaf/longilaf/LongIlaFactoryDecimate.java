package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaDecimate;

public class LongIlaFactoryDecimate {
    private LongIlaFactoryDecimate() {}

    public static LongIlaFactory create(LongIlaFactory ilaFactory, long factor, long[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> LongIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
