package tfw.immutable.iis.objectiis;

/**
 * This interface defines a factory that creates ObjectIis objects.
 */
public interface ObjectIisFactory<T> {
    /**
     * Create a ObjectIis object.
     *
     * @return the ObjectIis object.
     */
    ObjectIis<T> create();
}
// AUTO GENERATED FROM TEMPLATE
