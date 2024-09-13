package tfw.immutable.iis.byteiis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class ByteIisFromInputStreamTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> ByteIisFromInputStream.create(null));
    }

    @Test
    void testExceptions() throws IOException {
        final ExceptionInputStream eis = new ExceptionInputStream();
        final ByteIis byteIis = ByteIisFromInputStream.create(eis);

        assertThrows(IOException.class, () -> byteIis.skip(10));
        assertThrows(IOException.class, () -> byteIis.read(new byte[10], 0, 10));
        assertThrows(IOException.class, () -> byteIis.close());
    }

    @Test
    void testMethods() throws IOException {
        final TestInputStream tis = new TestInputStream();
        final ByteIis byteIis = ByteIisFromInputStream.create(tis);
        final long skipN = 11;
        final byte[] array = new byte[13];

        assertEquals(skipN, byteIis.skip(skipN));
        assertEquals(array.length, byteIis.read(array, 0, array.length));

        byteIis.close();

        assertTrue(tis.closeCalled);
        assertTrue(tis.readCalled);
        assertTrue(tis.skipCalled);
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
