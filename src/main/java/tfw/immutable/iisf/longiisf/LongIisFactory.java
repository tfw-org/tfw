package tfw.immutable.iisf.longiisf;

import java.io.IOException;
import tfw.immutable.iis.longiis.LongIis;

/**
 * This interface defines a factory that creates LongIis objects.
 */
public interface LongIisFactory {
    /**
     * Create a LongIis object.
     *
     * @return the LongIis object.
     */
    LongIis create() throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
