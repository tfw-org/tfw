package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIlaAdd;

public class IntIlaFactoryAdd {
    private IntIlaFactoryAdd() {}

    public static IntIlaFactory create(IntIlaFactory leftFactory, IntIlaFactory rightFactory, int bufferSize) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> IntIlaAdd.create(leftFactory.create(), rightFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
