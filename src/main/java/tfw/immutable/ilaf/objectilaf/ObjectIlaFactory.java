package tfw.immutable.ilaf.objectilaf;

import tfw.immutable.ila.objectila.ObjectIla;

/**
 * This interface defines a factory that creates ObjectIla objects.
 */
public interface ObjectIlaFactory<T> {
    /**
     * Create a ObjectIla object.
     *
     * @return the ObjectIla object.
     */
    ObjectIla<T> create();
}
// AUTO GENERATED FROM TEMPLATE
