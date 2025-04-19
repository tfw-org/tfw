package tfw.immutable.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;
import tfw.immutable.ilaf.longilaf.LongIlaFactoryFromArray;

final class LongStreamFactoryFromLongIlaFactoryTest {
    @Test
    void successTest() throws IOException {
        final long[] array = new long[] {-2, -1, 0, 1, 2};
        final LongIlaFactory i = LongIlaFactoryFromArray.create(array);
        final LongStreamFactory lsf = LongStreamFactoryFromLongIlaFactory.create(i);
        final LongStream s = lsf.create();
        final long[] list = s.toArray();

        assertThat(list).isEqualTo(array);
    }

    @Test
    void badIlaLengthTest() throws IOException {
        LongIlaFactory l = new LongIlaFactory() {
            @Override
            public LongIla create() {
                return new LongIla() {
                    @Override
                    public long length() throws IOException {
                        throw new IOException("Test LongIla that only throws IOExceptions!");
                    }

                    @Override
                    public void get(long[] array, int arrayOffset, long ilaStart, int length) throws IOException {
                        throw new IOException("Test LongIla that only throws IOExceptions!");
                    }
                };
            }
        };
        final LongStreamFactory lsf = LongStreamFactoryFromLongIlaFactory.create(l);

        assertThatThrownBy(() -> lsf.create())
                .isInstanceOf(IOException.class)
                .hasMessage("Test LongIla that only throws IOExceptions!");
    }

    @Test
    void badIlaGetTest() throws IOException {
        LongIlaFactory l = new LongIlaFactory() {
            @Override
            public LongIla create() {
                return new LongIla() {
                    @Override
                    public long length() throws IOException {
                        return 5;
                    }

                    @Override
                    public void get(long[] array, int arrayOffset, long ilaStart, int length) throws IOException {
                        throw new IOException("Test LongIla whose get() only throws IOExceptions!");
                    }
                };
            }
        };
        final LongStreamFactory lsf = LongStreamFactoryFromLongIlaFactory.create(l);
        final LongStream s = lsf.create();

        assertThatThrownBy(() -> s.toArray())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("End size 0 is less than fixed size 5");
    }
}
