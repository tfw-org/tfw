package tfw.immutable.ilaf.booleanilaf;

import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIlaDecimate;

public class BooleanIlaFactoryDecimate {
    private BooleanIlaFactoryDecimate() {}

    public static BooleanIlaFactory create(BooleanIlaFactory ilaFactory, long factor, boolean[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> BooleanIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
