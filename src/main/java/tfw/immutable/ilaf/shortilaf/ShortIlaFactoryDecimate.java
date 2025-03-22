package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIlaDecimate;

public class ShortIlaFactoryDecimate {
    private ShortIlaFactoryDecimate() {}

    public static ShortIlaFactory create(ShortIlaFactory ilaFactory, long factor, short[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> ShortIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
