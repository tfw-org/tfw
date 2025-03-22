package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaFiltered;
import tfw.immutable.ila.longila.LongIlaFiltered.LongFilter;

public class LongIlaFactoryFiltered {
    private LongIlaFactoryFiltered() {}

    public static LongIlaFactory create(LongIlaFactory ilaFactory, LongFilter filter, long[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> LongIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
