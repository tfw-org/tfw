package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaFromCastLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class CharIlaFactoryFromCastLongIlaFactory {
    private CharIlaFactoryFromCastLongIlaFactory() {}

    public static CharIlaFactory create(LongIlaFactory longIlaFactory, final int bufferSize) {
        Argument.assertNotNull(longIlaFactory, "longIlaFactory");

        return () -> CharIlaFromCastLongIla.create(longIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
