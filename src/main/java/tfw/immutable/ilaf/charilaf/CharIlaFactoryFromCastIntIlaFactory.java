package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaFromCastIntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class CharIlaFactoryFromCastIntIlaFactory {
    private CharIlaFactoryFromCastIntIlaFactory() {}

    public static CharIlaFactory create(IntIlaFactory intIlaFactory, final int bufferSize) {
        Argument.assertNotNull(intIlaFactory, "intIlaFactory");

        return () -> CharIlaFromCastIntIla.create(intIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
