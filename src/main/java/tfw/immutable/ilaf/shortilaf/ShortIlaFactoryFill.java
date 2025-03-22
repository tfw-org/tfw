package tfw.immutable.ilaf.shortilaf;

import tfw.immutable.ila.shortila.ShortIlaFill;

public class ShortIlaFactoryFill {
    private ShortIlaFactoryFill() {}

    public static ShortIlaFactory create(final short value, final long length) {
        return () -> ShortIlaFill.create(value, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
