package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaFiltered;
import tfw.immutable.ila.doubleila.DoubleIlaFiltered.DoubleFilter;

public class DoubleIlaFactoryFiltered {
    private DoubleIlaFactoryFiltered() {}

    public static DoubleIlaFactory create(DoubleIlaFactory ilaFactory, DoubleFilter filter, double[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> DoubleIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
