package tfw.immutable.iisf.chariisf;

import java.io.IOException;
import tfw.immutable.iis.chariis.CharIis;

/**
 * This interface defines a factory that creates CharIis objects.
 */
public interface CharIisFactory {
    /**
     * Create a CharIis object.
     *
     * @return the CharIis object.
     */
    CharIis create() throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
