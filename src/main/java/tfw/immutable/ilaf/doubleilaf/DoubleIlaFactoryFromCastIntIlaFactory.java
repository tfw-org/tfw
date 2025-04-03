package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastIntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class DoubleIlaFactoryFromCastIntIlaFactory {
    private DoubleIlaFactoryFromCastIntIlaFactory() {}

    public static DoubleIlaFactory create(IntIlaFactory intIlaFactory, final int bufferSize) {
        Argument.assertNotNull(intIlaFactory, "intIlaFactory");

        return () -> DoubleIlaFromCastIntIla.create(intIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
