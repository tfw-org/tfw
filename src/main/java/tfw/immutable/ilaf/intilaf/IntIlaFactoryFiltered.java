package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIlaFiltered;
import tfw.immutable.ila.intila.IntIlaFiltered.IntFilter;

public class IntIlaFactoryFiltered {
    private IntIlaFactoryFiltered() {}

    public static IntIlaFactory create(IntIlaFactory ilaFactory, IntFilter filter, int[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> IntIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
