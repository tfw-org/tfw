package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaFromCastCharIla;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;

public class FloatIlaFactoryFromCastCharIlaFactory {
    private FloatIlaFactoryFromCastCharIlaFactory() {}

    public static FloatIlaFactory create(CharIlaFactory charIlaFactory, final int bufferSize) {
        Argument.assertNotNull(charIlaFactory, "charIlaFactory");

        return () -> FloatIlaFromCastCharIla.create(charIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
