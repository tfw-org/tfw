package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaBound;

public class DoubleIlaFactoryBound {
    private DoubleIlaFactoryBound() {}

    public static DoubleIlaFactory create(DoubleIlaFactory ilaFactory, double minimum, double maximum) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> DoubleIlaBound.create(ilaFactory.create(), minimum, maximum);
    }
}
// AUTO GENERATED FROM TEMPLATE
