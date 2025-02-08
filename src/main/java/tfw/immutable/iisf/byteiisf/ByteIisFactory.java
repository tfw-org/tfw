package tfw.immutable.iisf.byteiisf;

import java.io.IOException;
import tfw.immutable.iis.byteiis.ByteIis;

/**
 * This interface defines a factory that creates ByteIis objects.
 */
public interface ByteIisFactory {
    /**
     * Create a ByteIis object.
     *
     * @return the ByteIis object.
     */
    ByteIis create() throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
