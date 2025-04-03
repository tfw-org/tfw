package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIlaConcatenate;

public class IntIlaFactoryConcatenate {
    private IntIlaFactoryConcatenate() {}

    public static IntIlaFactory create(IntIlaFactory leftFactory, IntIlaFactory rightFactory) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> IntIlaConcatenate.create(leftFactory.create(), rightFactory.create());
    }
}
// AUTO GENERATED FROM TEMPLATE
