package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaConcatenate;

public class LongIlaFactoryConcatenate {
    private LongIlaFactoryConcatenate() {}

    public static LongIlaFactory create(LongIlaFactory leftFactory, LongIlaFactory rightFactory) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> LongIlaConcatenate.create(leftFactory.create(), rightFactory.create());
    }
}
// AUTO GENERATED FROM TEMPLATE
