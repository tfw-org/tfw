package tfw.immutable.iis.byteiis;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamFactory {
    InputStream create() throws IOException;
}
