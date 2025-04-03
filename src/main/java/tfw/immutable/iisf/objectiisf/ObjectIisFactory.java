package tfw.immutable.iisf.objectiisf;

import java.io.IOException;
import tfw.immutable.iis.objectiis.ObjectIis;

/**
 * This interface defines a factory that creates ObjectIis objects.
 */
public interface ObjectIisFactory<T> {
    /**
     * Create a ObjectIis object.
     *
     * @return the ObjectIis object.
     */
    ObjectIis<T> create() throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
