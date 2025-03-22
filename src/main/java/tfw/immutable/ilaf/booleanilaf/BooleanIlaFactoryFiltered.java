package tfw.immutable.ilaf.booleanilaf;

import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIlaFiltered;
import tfw.immutable.ila.booleanila.BooleanIlaFiltered.BooleanFilter;

public class BooleanIlaFactoryFiltered {
    private BooleanIlaFactoryFiltered() {}

    public static BooleanIlaFactory create(BooleanIlaFactory ilaFactory, BooleanFilter filter, boolean[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> BooleanIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
