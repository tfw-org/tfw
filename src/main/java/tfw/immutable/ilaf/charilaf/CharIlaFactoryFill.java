package tfw.immutable.ilaf.charilaf;

import tfw.immutable.ila.charila.CharIlaFill;

public class CharIlaFactoryFill {
    private CharIlaFactoryFill() {}

    public static CharIlaFactory create(final char value, final long length) {
        return () -> CharIlaFill.create(value, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
