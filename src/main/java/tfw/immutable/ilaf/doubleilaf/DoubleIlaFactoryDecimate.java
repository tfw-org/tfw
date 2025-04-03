package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaDecimate;

public class DoubleIlaFactoryDecimate {
    private DoubleIlaFactoryDecimate() {}

    public static DoubleIlaFactory create(DoubleIlaFactory ilaFactory, long factor, double[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> DoubleIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
