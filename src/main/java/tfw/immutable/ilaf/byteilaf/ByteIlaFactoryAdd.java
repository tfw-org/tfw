package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaAdd;

public class ByteIlaFactoryAdd {
    private ByteIlaFactoryAdd() {}

    public static ByteIlaFactory create(ByteIlaFactory leftFactory, ByteIlaFactory rightFactory, int bufferSize) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> ByteIlaAdd.create(leftFactory.create(), rightFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
