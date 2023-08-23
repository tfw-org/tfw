package tfw.immutable.ila;

import java.io.IOException;
import tfw.check.Argument;

public abstract class AbstractIla implements ImmutableLongArray {
    protected abstract long lengthImpl() throws IOException;

    private long length = -1;

    protected AbstractIla() {}

    public final long length() throws IOException {
        if (length < 0) {
            length = lengthImpl();

            Argument.assertNotLessThan(length, 0, "length");
        }

        return length;
    }

    protected final void boundsCheck(int arrayLength, int offset, long start, int length) throws IOException {
        AbstractIlaCheck.boundsCheck(length(), arrayLength, offset, start, length);
    }
}
