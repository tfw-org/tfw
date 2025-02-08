package tfw.immutable.iisf.booleaniisf;

import java.io.IOException;
import tfw.immutable.iis.booleaniis.BooleanIis;

/**
 * This interface defines a factory that creates BooleanIis objects.
 */
public interface BooleanIisFactory {
    /**
     * Create a BooleanIis object.
     *
     * @return the BooleanIis object.
     */
    BooleanIis create() throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
