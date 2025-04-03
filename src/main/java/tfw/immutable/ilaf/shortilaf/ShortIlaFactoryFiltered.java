package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIlaFiltered;
import tfw.immutable.ila.shortila.ShortIlaFiltered.ShortFilter;

public class ShortIlaFactoryFiltered {
    private ShortIlaFactoryFiltered() {}

    public static ShortIlaFactory create(ShortIlaFactory ilaFactory, ShortFilter filter, short[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> ShortIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
