package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaConcatenate;

public class DoubleIlaFactoryConcatenate {
    private DoubleIlaFactoryConcatenate() {}

    public static DoubleIlaFactory create(DoubleIlaFactory leftFactory, DoubleIlaFactory rightFactory) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> DoubleIlaConcatenate.create(leftFactory.create(), rightFactory.create());
    }
}
// AUTO GENERATED FROM TEMPLATE
