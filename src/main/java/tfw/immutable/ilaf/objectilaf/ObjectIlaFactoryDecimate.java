package tfw.immutable.ilaf.objectilaf;

import tfw.check.Argument;
import tfw.immutable.ila.objectila.ObjectIlaDecimate;

public class ObjectIlaFactoryDecimate {
    private ObjectIlaFactoryDecimate() {}

    public static <T> ObjectIlaFactory<T> create(ObjectIlaFactory<T> ilaFactory, long factor, T[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> ObjectIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
