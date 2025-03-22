package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaFiltered;
import tfw.immutable.ila.byteila.ByteIlaFiltered.ByteFilter;

public class ByteIlaFactoryFiltered {
    private ByteIlaFactoryFiltered() {}

    public static ByteIlaFactory create(ByteIlaFactory ilaFactory, ByteFilter filter, byte[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> ByteIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
