package tfw.immutable.iisf.doubleiisf;

import java.io.IOException;
import tfw.immutable.iis.doubleiis.DoubleIis;

/**
 * This interface defines a factory that creates DoubleIis objects.
 */
public interface DoubleIisFactory {
    /**
     * Create a DoubleIis object.
     *
     * @return the DoubleIis object.
     */
    DoubleIis create() throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
