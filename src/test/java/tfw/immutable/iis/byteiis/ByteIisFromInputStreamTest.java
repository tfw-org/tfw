package tfw.immutable.iis.byteiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

final class ByteIisFromInputStreamTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> ByteIisFromInputStream.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void exceptionsTest() {
        final ExceptionInputStream eis = new ExceptionInputStream();
        final ByteIis byteIis = ByteIisFromInputStream.create(eis);

        assertThatThrownBy(() -> byteIis.skip(10)).isInstanceOf(IOException.class);
        assertThatThrownBy(() -> byteIis.read(new byte[10], 0, 10)).isInstanceOf(IOException.class);
        assertThatThrownBy(byteIis::close).isInstanceOf(IOException.class);
    }

    @Test
    void methodsTest() throws IOException {
        final TestInputStream tis = new TestInputStream();
        final ByteIis byteIis = ByteIisFromInputStream.create(tis);
        final long skipN = 11;
        final byte[] array = new byte[13];

        assertThat(byteIis.skip(skipN)).isEqualTo(skipN);
        assertThat(byteIis.read(array, 0, array.length)).isEqualTo(array.length);

        byteIis.close();

        assertThat(tis.closeCalled).isTrue();
        assertThat(tis.readCalled).isTrue();
        assertThat(tis.skipCalled).isTrue();
    }

    private static class ExceptionInputStream extends InputStream {
        @Override
        public void close() throws IOException {
            throw new IOException("This test class only throws Exceptions!");
        }

        @Override
        public int read() throws IOException {
            throw new IOException("This test class only throws Exceptions!");
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            throw new IOException("This test class only throws Exceptions!");
        }

        @Override
        public long skip(long arg0) throws IOException {
            throw new IOException("This test class only throws Exceptions!");
        }
    }

    private static class TestInputStream extends InputStream {
        public boolean closeCalled = false;
        public boolean readCalled = false;
        public boolean skipCalled = false;

        @Override
        public void close() throws IOException {
            closeCalled = true;
        }

        @Override
        public int read() throws IOException {
            throw new IOException("Read requires implementation, but not used!");
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            readCalled = true;

            return len;
        }

        @Override
        public long skip(long n) throws IOException {
            skipCalled = true;

            return n;
        }
    }
}
