package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastCharIla;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;

public class DoubleIlaFactoryFromCastCharIlaFactory {
    private DoubleIlaFactoryFromCastCharIlaFactory() {}

    public static DoubleIlaFactory create(CharIlaFactory charIlaFactory, final int bufferSize) {
        Argument.assertNotNull(charIlaFactory, "charIlaFactory");

        return () -> DoubleIlaFromCastCharIla.create(charIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
