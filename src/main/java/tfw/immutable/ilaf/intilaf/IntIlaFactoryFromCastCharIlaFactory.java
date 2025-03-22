package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIlaFromCastCharIla;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;

public class IntIlaFactoryFromCastCharIlaFactory {
    private IntIlaFactoryFromCastCharIlaFactory() {}

    public static IntIlaFactory create(CharIlaFactory charIlaFactory, final int bufferSize) {
        Argument.assertNotNull(charIlaFactory, "charIlaFactory");

        return () -> IntIlaFromCastCharIla.create(charIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
