package tfw.immutable.ila;

import java.io.IOException;

public abstract class AbstractIla implements ImmutableLongArray {
    protected abstract long lengthImpl() throws IOException;

    protected AbstractIla() {}

    @Override
    public final long length() throws IOException {
        return lengthImpl();
    }

    protected final void boundsCheck(int arrayLength, int offset, long start, int length) throws IOException {
        ImmutableLongArrayUtil.boundsCheck(length(), arrayLength, offset, start, length);
    }
}
