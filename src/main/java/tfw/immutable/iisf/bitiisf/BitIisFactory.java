package tfw.immutable.iisf.bitiisf;

import java.io.IOException;
import tfw.immutable.iis.bitiis.BitIis;

/**
 * This interface defines a factory that creates BooleanIis objects.
 */
public interface BitIisFactory {
    /**
     * Create a BooleanIis object.
     *
     * @return the BooleanIis object.
     */
    BitIis create() throws IOException;
}
