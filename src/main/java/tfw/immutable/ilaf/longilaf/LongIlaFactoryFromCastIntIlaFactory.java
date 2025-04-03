package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaFromCastIntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class LongIlaFactoryFromCastIntIlaFactory {
    private LongIlaFactoryFromCastIntIlaFactory() {}

    public static LongIlaFactory create(IntIlaFactory intIlaFactory, final int bufferSize) {
        Argument.assertNotNull(intIlaFactory, "intIlaFactory");

        return () -> LongIlaFromCastIntIla.create(intIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
