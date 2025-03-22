package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIlaFromCastLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class ShortIlaFactoryFromCastLongIlaFactory {
    private ShortIlaFactoryFromCastLongIlaFactory() {}

    public static ShortIlaFactory create(LongIlaFactory longIlaFactory, final int bufferSize) {
        Argument.assertNotNull(longIlaFactory, "longIlaFactory");

        return () -> ShortIlaFromCastLongIla.create(longIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
