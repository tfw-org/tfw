package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaBound;

public class LongIlaFactoryBound {
    private LongIlaFactoryBound() {}

    public static LongIlaFactory create(LongIlaFactory ilaFactory, long minimum, long maximum) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> LongIlaBound.create(ilaFactory.create(), minimum, maximum);
    }
}
// AUTO GENERATED FROM TEMPLATE
