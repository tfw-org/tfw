package tfw.immutable.iba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.ClosedManager;

public abstract class AbstractIba implements ImmutableBigIntegerArray {
    protected abstract void closeImpl() throws IOException;

    protected abstract BigInteger lengthImpl() throws IOException;

    private final ClosedManager closedManager = new ClosedManager();

    @Override
    public final void close() throws IOException {
        if (closedManager.close()) {
            closeImpl();
        }
    }

    @Override
    public final BigInteger length() throws IOException {
        checkClosed();

        return lengthImpl();
    }

    protected void checkClosed() {
        closedManager.checkClosed();
    }
}
