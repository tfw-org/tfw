package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIlaAdd;

public class ShortIlaFactoryAdd {
    private ShortIlaFactoryAdd() {}

    public static ShortIlaFactory create(ShortIlaFactory leftFactory, ShortIlaFactory rightFactory, int bufferSize) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> ShortIlaAdd.create(leftFactory.create(), rightFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
