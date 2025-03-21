package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIlaInsert;

public class ShortIlaFactoryInsert {
    private ShortIlaFactoryInsert() {}

    public static ShortIlaFactory create(ShortIlaFactory ilaFactory, long index, short value) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> ShortIlaInsert.create(ilaFactory.create(), index, value);
    }
}
// AUTO GENERATED FROM TEMPLATE
