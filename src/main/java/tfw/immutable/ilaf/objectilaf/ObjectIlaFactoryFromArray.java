package tfw.immutable.ilaf.objectilaf;

import tfw.immutable.ila.objectila.ObjectIlaFromArray;

public class ObjectIlaFactoryFromArray {
    private ObjectIlaFactoryFromArray() {}

    public static <T> ObjectIlaFactory<T> create(final T[] array) {
        return () -> ObjectIlaFromArray.create(array);
    }
}
// AUTO GENERATED FROM TEMPLATE
