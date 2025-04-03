package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaFromCastShortIla;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;

public class LongIlaFactoryFromCastShortIlaFactory {
    private LongIlaFactoryFromCastShortIlaFactory() {}

    public static LongIlaFactory create(ShortIlaFactory shortIlaFactory, final int bufferSize) {
        Argument.assertNotNull(shortIlaFactory, "shortIlaFactory");

        return () -> LongIlaFromCastShortIla.create(shortIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
