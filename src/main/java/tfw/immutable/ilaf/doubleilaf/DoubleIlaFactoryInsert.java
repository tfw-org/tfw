package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaInsert;

public class DoubleIlaFactoryInsert {
    private DoubleIlaFactoryInsert() {}

    public static DoubleIlaFactory create(DoubleIlaFactory ilaFactory, long index, double value) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> DoubleIlaInsert.create(ilaFactory.create(), index, value);
    }
}
// AUTO GENERATED FROM TEMPLATE
