package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaConcatenate;

public class CharIlaFactoryConcatenate {
    private CharIlaFactoryConcatenate() {}

    public static CharIlaFactory create(CharIlaFactory leftFactory, CharIlaFactory rightFactory) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> CharIlaConcatenate.create(leftFactory.create(), rightFactory.create());
    }
}
// AUTO GENERATED FROM TEMPLATE
