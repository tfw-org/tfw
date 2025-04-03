package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaFromCastShortIla;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;

public class CharIlaFactoryFromCastShortIlaFactory {
    private CharIlaFactoryFromCastShortIlaFactory() {}

    public static CharIlaFactory create(ShortIlaFactory shortIlaFactory, final int bufferSize) {
        Argument.assertNotNull(shortIlaFactory, "shortIlaFactory");

        return () -> CharIlaFromCastShortIla.create(shortIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
