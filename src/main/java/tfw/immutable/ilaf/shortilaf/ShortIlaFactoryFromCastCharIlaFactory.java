package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIlaFromCastCharIla;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;

public class ShortIlaFactoryFromCastCharIlaFactory {
    private ShortIlaFactoryFromCastCharIlaFactory() {}

    public static ShortIlaFactory create(CharIlaFactory charIlaFactory, final int bufferSize) {
        Argument.assertNotNull(charIlaFactory, "charIlaFactory");

        return () -> ShortIlaFromCastCharIla.create(charIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
