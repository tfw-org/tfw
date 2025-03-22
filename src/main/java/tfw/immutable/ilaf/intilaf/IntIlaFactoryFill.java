package tfw.immutable.ilaf.intilaf;

import tfw.immutable.ila.intila.IntIlaFill;

public class IntIlaFactoryFill {
    private IntIlaFactoryFill() {}

    public static IntIlaFactory create(final int value, final long length) {
        return () -> IntIlaFill.create(value, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
