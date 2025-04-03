package tfw.immutable.ilaf.doubleilaf;

import tfw.immutable.ila.doubleila.DoubleIlaFill;

public class DoubleIlaFactoryFill {
    private DoubleIlaFactoryFill() {}

    public static DoubleIlaFactory create(final double value, final long length) {
        return () -> DoubleIlaFill.create(value, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
