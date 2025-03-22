package tfw.immutable.ilaf.objectilaf;

import tfw.immutable.ila.objectila.ObjectIlaFill;

public class ObjectIlaFactoryFill {
    private ObjectIlaFactoryFill() {}

    public static <T> ObjectIlaFactory<T> create(final T value, final long length) {
        return () -> ObjectIlaFill.create(value, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
