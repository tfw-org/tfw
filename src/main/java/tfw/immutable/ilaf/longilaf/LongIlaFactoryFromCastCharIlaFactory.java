package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaFromCastCharIla;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;

public class LongIlaFactoryFromCastCharIlaFactory {
    private LongIlaFactoryFromCastCharIlaFactory() {}

    public static LongIlaFactory create(CharIlaFactory charIlaFactory, final int bufferSize) {
        Argument.assertNotNull(charIlaFactory, "charIlaFactory");

        return () -> LongIlaFromCastCharIla.create(charIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
