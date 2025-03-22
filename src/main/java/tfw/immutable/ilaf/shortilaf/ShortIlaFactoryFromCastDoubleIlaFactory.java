package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIlaFromCastDoubleIla;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;

public class ShortIlaFactoryFromCastDoubleIlaFactory {
    private ShortIlaFactoryFromCastDoubleIlaFactory() {}

    public static ShortIlaFactory create(DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
        Argument.assertNotNull(doubleIlaFactory, "doubleIlaFactory");

        return () -> ShortIlaFromCastDoubleIla.create(doubleIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
