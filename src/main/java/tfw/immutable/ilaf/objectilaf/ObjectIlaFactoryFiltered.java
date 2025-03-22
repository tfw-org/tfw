package tfw.immutable.ilaf.objectilaf;

import tfw.check.Argument;
import tfw.immutable.ila.objectila.ObjectIlaFiltered;
import tfw.immutable.ila.objectila.ObjectIlaFiltered.ObjectFilter;

public class ObjectIlaFactoryFiltered {
    private ObjectIlaFactoryFiltered() {}

    public static <T> ObjectIlaFactory<T> create(ObjectIlaFactory<T> ilaFactory, ObjectFilter<T> filter, T[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> ObjectIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
