package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaAdd;

public class LongIlaFactoryAdd {
    private LongIlaFactoryAdd() {}

    public static LongIlaFactory create(LongIlaFactory leftFactory, LongIlaFactory rightFactory, int bufferSize) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> LongIlaAdd.create(leftFactory.create(), rightFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
