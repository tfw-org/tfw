package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaDivide;

public class ByteIlaFactoryDivide {
    private ByteIlaFactoryDivide() {}

    public static ByteIlaFactory create(
            final ByteIlaFactory leftFactory, final ByteIlaFactory rightFactory, final int bufferSize) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> ByteIlaDivide.create(leftFactory.create(), rightFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
