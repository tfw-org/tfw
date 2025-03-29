package tfw.check;

public class ClosedManager {
    private boolean closed = false;

    public boolean close() {
        if (!closed) {
            closed = true;

            return true;
        }

        return false;
    }

    public void checkClosed(final String name) {
        if (closed) {
            throw new IllegalStateException(String.format("This %s is closed!", name));
        }
    }
}
