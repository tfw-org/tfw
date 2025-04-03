package tfw.immutable.ilaf.longilaf;

import tfw.immutable.ila.longila.LongIlaFill;

public class LongIlaFactoryFill {
    private LongIlaFactoryFill() {}

    public static LongIlaFactory create(final long value, final long length) {
        return () -> LongIlaFill.create(value, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
