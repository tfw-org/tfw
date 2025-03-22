package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIlaFromCastLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class IntIlaFactoryFromCastLongIlaFactory {
    private IntIlaFactoryFromCastLongIlaFactory() {}

    public static IntIlaFactory create(LongIlaFactory longIlaFactory, final int bufferSize) {
        Argument.assertNotNull(longIlaFactory, "longIlaFactory");

        return () -> IntIlaFromCastLongIla.create(longIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
