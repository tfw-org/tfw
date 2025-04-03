package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaInsert;

public class CharIlaFactoryInsert {
    private CharIlaFactoryInsert() {}

    public static CharIlaFactory create(CharIlaFactory ilaFactory, long index, char value) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> CharIlaInsert.create(ilaFactory.create(), index, value);
    }
}
// AUTO GENERATED FROM TEMPLATE
