package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaBound;

public class ByteIlaFactoryBound {
    private ByteIlaFactoryBound() {}

    public static ByteIlaFactory create(ByteIlaFactory ilaFactory, byte minimum, byte maximum) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> ByteIlaBound.create(ilaFactory.create(), minimum, maximum);
    }
}
// AUTO GENERATED FROM TEMPLATE
