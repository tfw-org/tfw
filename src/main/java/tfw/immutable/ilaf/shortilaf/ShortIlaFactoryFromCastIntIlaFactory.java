package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIlaFromCastIntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class ShortIlaFactoryFromCastIntIlaFactory {
    private ShortIlaFactoryFromCastIntIlaFactory() {}

    public static ShortIlaFactory create(IntIlaFactory intIlaFactory, final int bufferSize) {
        Argument.assertNotNull(intIlaFactory, "intIlaFactory");

        return () -> ShortIlaFromCastIntIla.create(intIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
