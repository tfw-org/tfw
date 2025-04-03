package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIlaDivide;

public class ShortIlaFactoryDivide {
    private ShortIlaFactoryDivide() {}

    public static ShortIlaFactory create(
            final ShortIlaFactory leftFactory, final ShortIlaFactory rightFactory, final int bufferSize) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> ShortIlaDivide.create(leftFactory.create(), rightFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
