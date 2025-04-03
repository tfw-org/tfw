package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaDivide;

public class CharIlaFactoryDivide {
    private CharIlaFactoryDivide() {}

    public static CharIlaFactory create(
            final CharIlaFactory leftFactory, final CharIlaFactory rightFactory, final int bufferSize) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> CharIlaDivide.create(leftFactory.create(), rightFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
