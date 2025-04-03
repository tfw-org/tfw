package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaDecimate;

public class CharIlaFactoryDecimate {
    private CharIlaFactoryDecimate() {}

    public static CharIlaFactory create(CharIlaFactory ilaFactory, long factor, char[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> CharIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
