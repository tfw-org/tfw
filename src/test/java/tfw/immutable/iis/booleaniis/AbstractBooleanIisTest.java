package tfw.immutable.iis.booleaniis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class AbstractBooleanIisTest {
    @Test
    void testArguments() throws IOException {
        try (final TestBooleanIis ti = new TestBooleanIis()) {
            final boolean[] array = new boolean[11];

            assertThrows(IllegalArgumentException.class, () -> ti.read(null, 0, 1));
            assertThrows(IllegalArgumentException.class, () -> ti.read(array, -1, 1));
            assertThrows(IllegalArgumentException.class, () -> ti.read(array, 0, -1));
            assertThrows(IllegalArgumentException.class, () -> ti.read(array, array.length, 1));
            assertEquals(0, ti.read(array, 0, 0));
            assertEquals(1, ti.read(array, 0, 1));
            assertEquals(0, ti.skip(0));
            assertEquals(1, ti.skip(1));
        }
    }

    @Test
    void testClosed() throws IOException {
        final TestBooleanIis ti = new TestBooleanIis();
        final boolean[] array = new boolean[11];

        ti.close();

        assertTrue(ti.closeCalled);
        assertEquals(-1, ti.read(array, 0, array.length));
        assertEquals(-1, ti.skip(10));

        ti.closeCalled = false;
        ti.close();

        assertFalse(ti.closeCalled);
    }

    private class TestBooleanIis extends AbstractBooleanIis {
        public boolean closeCalled = false;

        @Override
        protected void closeImpl() throws IOException {
            closeCalled = true;
        }

        @Override
        protected int readImpl(boolean[] array, int offset, int length) throws IOException {
            return length;
        }

        @Override
        protected long skipImpl(long n) throws IOException {
            return n;
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
