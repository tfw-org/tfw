package tfw.immutable.iisf.shortiisf;

import java.io.IOException;
import tfw.immutable.iis.shortiis.ShortIis;

/**
 * This interface defines a factory that creates ShortIis objects.
 */
public interface ShortIisFactory {
    /**
     * Create a ShortIis object.
     *
     * @return the ShortIis object.
     */
    ShortIis create() throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
