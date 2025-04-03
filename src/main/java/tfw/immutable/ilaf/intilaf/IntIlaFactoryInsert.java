package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIlaInsert;

public class IntIlaFactoryInsert {
    private IntIlaFactoryInsert() {}

    public static IntIlaFactory create(IntIlaFactory ilaFactory, long index, int value) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> IntIlaInsert.create(ilaFactory.create(), index, value);
    }
}
// AUTO GENERATED FROM TEMPLATE
