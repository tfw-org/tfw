package tfw.immutable.ilaf.booleanilaf;

import tfw.immutable.ila.booleanila.BooleanIlaFill;

public class BooleanIlaFactoryFill {
    private BooleanIlaFactoryFill() {}

    public static BooleanIlaFactory create(final boolean value, final long length) {
        return () -> BooleanIlaFill.create(value, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
