package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIlaDecimate;

public class IntIlaFactoryDecimate {
    private IntIlaFactoryDecimate() {}

    public static IntIlaFactory create(IntIlaFactory ilaFactory, long factor, int[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> IntIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
