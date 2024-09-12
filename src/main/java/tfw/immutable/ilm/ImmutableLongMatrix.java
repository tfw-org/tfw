package tfw.immutable.ilm;

import java.io.IOException;

public interface ImmutableLongMatrix {
    long width() throws IOException;

    long height() throws IOException;
}
