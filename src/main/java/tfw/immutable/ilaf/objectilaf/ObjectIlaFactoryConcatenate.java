package tfw.immutable.ilaf.objectilaf;

import tfw.check.Argument;
import tfw.immutable.ila.objectila.ObjectIlaConcatenate;

public class ObjectIlaFactoryConcatenate {
    private ObjectIlaFactoryConcatenate() {}

    public static <T> ObjectIlaFactory<T> create(ObjectIlaFactory<T> leftFactory, ObjectIlaFactory<T> rightFactory) {
        Argument.assertNotNull(leftFactory, "leftFactory");
        Argument.assertNotNull(rightFactory, "rightFactory");

        return () -> ObjectIlaConcatenate.create(leftFactory.create(), rightFactory.create());
    }
}
// AUTO GENERATED FROM TEMPLATE
