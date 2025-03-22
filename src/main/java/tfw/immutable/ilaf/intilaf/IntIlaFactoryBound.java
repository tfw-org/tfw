package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIlaBound;

public class IntIlaFactoryBound {
    private IntIlaFactoryBound() {}

    public static IntIlaFactory create(IntIlaFactory ilaFactory, int minimum, int maximum) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> IntIlaBound.create(ilaFactory.create(), minimum, maximum);
    }
}
// AUTO GENERATED FROM TEMPLATE
