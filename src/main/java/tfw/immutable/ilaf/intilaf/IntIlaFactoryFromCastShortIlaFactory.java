package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIlaFromCastShortIla;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;

public class IntIlaFactoryFromCastShortIlaFactory {
    private IntIlaFactoryFromCastShortIlaFactory() {}

    public static IntIlaFactory create(ShortIlaFactory shortIlaFactory, final int bufferSize) {
        Argument.assertNotNull(shortIlaFactory, "shortIlaFactory");

        return () -> IntIlaFromCastShortIla.create(shortIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
