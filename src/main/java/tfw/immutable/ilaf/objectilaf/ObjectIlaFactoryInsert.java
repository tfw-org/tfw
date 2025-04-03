package tfw.immutable.ilaf.objectilaf;

import tfw.check.Argument;
import tfw.immutable.ila.objectila.ObjectIlaInsert;

public class ObjectIlaFactoryInsert {
    private ObjectIlaFactoryInsert() {}

    public static <T> ObjectIlaFactory<T> create(ObjectIlaFactory<T> ilaFactory, long index, T value) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> ObjectIlaInsert.create(ilaFactory.create(), index, value);
    }
}
// AUTO GENERATED FROM TEMPLATE
