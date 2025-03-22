package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIlaBound;

public class ShortIlaFactoryBound {
    private ShortIlaFactoryBound() {}

    public static ShortIlaFactory create(ShortIlaFactory ilaFactory, short minimum, short maximum) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> ShortIlaBound.create(ilaFactory.create(), minimum, maximum);
    }
}
// AUTO GENERATED FROM TEMPLATE
