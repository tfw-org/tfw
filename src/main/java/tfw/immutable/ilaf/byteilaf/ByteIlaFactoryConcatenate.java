package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIlaConcatenate;

public class ByteIlaFactoryConcatenate {
    private ByteIlaFactoryConcatenate() {}

    public static ByteIlaFactory create(ByteIlaFactory leftFactory, ByteIlaFactory rightFactory) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> ByteIlaConcatenate.create(leftFactory.create(), rightFactory.create());
    }
}
// AUTO GENERATED FROM TEMPLATE
