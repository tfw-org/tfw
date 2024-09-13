package tfw.immutable.iis.byteiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class ByteIisFactoryFromInputStreamFactoryTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> ByteIisFactoryFromInputStreamFactory.create(null));
    }

    @Test
    void testCreate() throws IOException {
        final TestInputStreamFactory tisf = new TestInputStreamFactory();
        final ByteIisFactory bif = ByteIisFactoryFromInputStreamFactory.create(tisf);
        final ByteIis bi = bif.create();
        final byte[] array = new byte[tisf.array.length];

        assertEquals(array.length, bi.read(array, 0, array.length));
        assertEquals(-1, bi.read(array, 0, array.length));
        assertArrayEquals(tisf.array, array);
    }

    private static class TestInputStreamFactory implements InputStreamFactory {
        public final byte[] array = new byte[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

        @Override
        public InputStream create() {
            return new ByteArrayInputStream(array);
        }
    }
}
