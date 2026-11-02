package tfw.immutable.ila;

import java.io.IOException;
import tfw.check.ClosedManager;

public abstract class AbstractIla implements ImmutableLongArray {
    protected abstract void closeImpl() throws IOException;

    protected abstract long lengthImpl() throws IOException;

    private final ClosedManager closedManager = new ClosedManager();

    protected AbstractIla() {}

    @Override
    public final void close() throws IOException {
        if (closedManager.close()) {
            closeImpl();
        }
    }

    @Override
    public final long length() throws IOException {
        checkClosed();

        return lengthImpl();
    }

    protected final void boundsCheck(int arrayLength, int offset, long start, int length) throws IOException {
        ImmutableLongArrayUtil.boundsCheck(length(), arrayLength, offset, start, length);
    }

    protected void checkClosed() {
        closedManager.checkClosed();
    }
}
