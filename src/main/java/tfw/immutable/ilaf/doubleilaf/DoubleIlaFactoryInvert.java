package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaInvert;

public class DoubleIlaFactoryInvert {
    private DoubleIlaFactoryInvert() {}

    public static DoubleIlaFactory create(DoubleIlaFactory ilaFactory) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> DoubleIlaInvert.create(ilaFactory.create());
    }
}
// AUTO GENERATED FROM TEMPLATE
