package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaInsert;

public class LongIlaFactoryInsert {
    private LongIlaFactoryInsert() {}

    public static LongIlaFactory create(LongIlaFactory ilaFactory, long index, long value) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> LongIlaInsert.create(ilaFactory.create(), index, value);
    }
}
// AUTO GENERATED FROM TEMPLATE
