package tfw.immutable.iisf.floatiisf;

import java.io.IOException;
import tfw.immutable.iis.floatiis.FloatIis;

/**
 * This interface defines a factory that creates FloatIis objects.
 */
public interface FloatIisFactory {
    /**
     * Create a FloatIis object.
     *
     * @return the FloatIis object.
     */
    FloatIis create() throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
