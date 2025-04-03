package tfw.immutable.ilaf.booleanilaf;

import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIlaConcatenate;

public class BooleanIlaFactoryConcatenate {
    private BooleanIlaFactoryConcatenate() {}

    public static BooleanIlaFactory create(BooleanIlaFactory leftFactory, BooleanIlaFactory rightFactory) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> BooleanIlaConcatenate.create(leftFactory.create(), rightFactory.create());
    }
}
// AUTO GENERATED FROM TEMPLATE
