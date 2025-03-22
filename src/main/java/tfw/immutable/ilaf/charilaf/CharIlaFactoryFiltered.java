package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaFiltered;
import tfw.immutable.ila.charila.CharIlaFiltered.CharFilter;

public class CharIlaFactoryFiltered {
    private CharIlaFactoryFiltered() {}

    public static CharIlaFactory create(CharIlaFactory ilaFactory, CharFilter filter, char[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> CharIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
