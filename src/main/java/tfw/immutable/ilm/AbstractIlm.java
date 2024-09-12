package tfw.immutable.ilm;

import java.io.IOException;
import tfw.check.Argument;

public abstract class AbstractIlm implements ImmutableLongMatrix {
    protected abstract long widthImpl() throws IOException;

    protected abstract long heightImpl() throws IOException;

    private long width = -1;
    private long height = -1;

    protected AbstractIlm() {}

    @Override
    public final long width() throws IOException {
        getHeightWidth();

        return width;
    }

    @Override
    public final long height() throws IOException {
        getHeightWidth();

        return height;
    }

    private void getHeightWidth() throws IOException {
        if (width < 0) {
            width = widthImpl();
            height = heightImpl();

            Argument.assertNotLessThan(width, 0, "width");
            Argument.assertNotLessThan(height, 0, "height");

            if (width == 0 && height != 0) throw new IllegalArgumentException("width == 0 && height != 0 not allowed!");
            if (height == 0 && width != 0) throw new IllegalArgumentException("height == 0 && width != 0 not allowed!");
        }
    }
}
