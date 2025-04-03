package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaBound;

public class CharIlaFactoryBound {
    private CharIlaFactoryBound() {}

    public static CharIlaFactory create(CharIlaFactory ilaFactory, char minimum, char maximum) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> CharIlaBound.create(ilaFactory.create(), minimum, maximum);
    }
}
// AUTO GENERATED FROM TEMPLATE
