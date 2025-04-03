package tfw.immutable.iisf.intiisf;

import java.io.IOException;
import tfw.immutable.iis.intiis.IntIis;

/**
 * This interface defines a factory that creates IntIis objects.
 */
public interface IntIisFactory {
    /**
     * Create a IntIis object.
     *
     * @return the IntIis object.
     */
    IntIis create() throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
