package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaSegment;

public class CharIlaFactorySegment {
    private CharIlaFactorySegment() {}

    public static CharIlaFactory create(CharIlaFactory factory, final long start, final long length) {
        Argument.assertNotNull(factory, "factory");

        return () -> CharIlaSegment.create(factory.create(), start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
