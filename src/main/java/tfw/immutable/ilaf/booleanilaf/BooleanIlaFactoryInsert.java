package tfw.immutable.ilaf.booleanilaf;

import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIlaInsert;

public class BooleanIlaFactoryInsert {
    private BooleanIlaFactoryInsert() {}

    public static BooleanIlaFactory create(BooleanIlaFactory ilaFactory, long index, boolean value) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> BooleanIlaInsert.create(ilaFactory.create(), index, value);
    }
}
// AUTO GENERATED FROM TEMPLATE
