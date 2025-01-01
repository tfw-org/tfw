package tfw.immutable.iis.byteiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

final class ByteIisFactoryFromInputStreamFactoryTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> ByteIisFactoryFromInputStreamFactory.create(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createTest() throws IOException {
        final TestInputStreamFactory tisf = new TestInputStreamFactory();
        final ByteIisFactory bif = ByteIisFactoryFromInputStreamFactory.create(tisf);
        final ByteIis bi = bif.create();
        final byte[] array = new byte[tisf.array.length];

        assertThat(bi.read(array, 0, array.length)).isEqualTo(array.length);
        assertThat(bi.read(array, 0, array.length)).isEqualTo(-1);
        assertThat(array).isEqualTo(tisf.array);
    }

    private static class TestInputStreamFactory implements InputStreamFactory {
        public final byte[] array = new byte[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

        @Override
        public InputStream create() {
            return new ByteArrayInputStream(array);
        }
    }
}
