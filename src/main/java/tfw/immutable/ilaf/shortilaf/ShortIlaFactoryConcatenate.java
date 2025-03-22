package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIlaConcatenate;

public class ShortIlaFactoryConcatenate {
    private ShortIlaFactoryConcatenate() {}

    public static ShortIlaFactory create(ShortIlaFactory leftFactory, ShortIlaFactory rightFactory) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> ShortIlaConcatenate.create(leftFactory.create(), rightFactory.create());
    }
}
// AUTO GENERATED FROM TEMPLATE
