package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaFromCastDoubleIla;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;

public class CharIlaFactoryFromCastDoubleIlaFactory {
    private CharIlaFactoryFromCastDoubleIlaFactory() {}

    public static CharIlaFactory create(DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
        Argument.assertNotNull(doubleIlaFactory, "doubleIlaFactory");

        return () -> CharIlaFromCastDoubleIla.create(doubleIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
