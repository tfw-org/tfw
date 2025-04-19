package tfw.immutable.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;
import tfw.immutable.ilaf.intilaf.IntIlaFactoryFromArray;

final class IntStreamFactoryFromIntIlaFactoryTest {
    @Test
    void successTest() throws IOException {
        final int[] array = new int[] {-2, -1, 0, 1, 2};
        final IntIlaFactory i = IntIlaFactoryFromArray.create(array);
        final IntStreamFactory isf = IntStreamFactoryFromIntIlaFactory.create(i);
        final IntStream s = isf.create();
        final int[] list = s.toArray();

        assertThat(list).isEqualTo(array);
    }

    @Test
    void badIlaLengthTest() throws IOException {
        IntIlaFactory i = new IntIlaFactory() {
            @Override
            public IntIla create() {
                return new IntIla() {
                    @Override
                    public long length() throws IOException {
                        throw new IOException("Test IntIla that only throws IOExceptions!");
                    }

                    @Override
                    public void get(int[] array, int arrayOffset, long ilaStart, int length) throws IOException {
                        throw new IOException("Test IntIla that only throws IOExceptions!");
                    }
                };
            }
        };
        final IntStreamFactory isf = IntStreamFactoryFromIntIlaFactory.create(i);

        assertThatThrownBy(() -> isf.create())
                .isInstanceOf(IOException.class)
                .hasMessage("Test IntIla that only throws IOExceptions!");
    }

    @Test
    void badIlaGetTest() throws IOException {
        IntIlaFactory i = new IntIlaFactory() {
            @Override
            public IntIla create() {
                return new IntIla() {
                    @Override
                    public long length() throws IOException {
                        return 5;
                    }

                    @Override
                    public void get(int[] array, int arrayOffset, long ilaStart, int length) throws IOException {
                        throw new IOException("Test IntIla whose get() only throws IOExceptions!");
                    }
                };
            }
        };
        final IntStreamFactory isf = IntStreamFactoryFromIntIlaFactory.create(i);
        final IntStream s = isf.create();

        assertThatThrownBy(() -> s.toArray())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("End size 0 is less than fixed size 5");
    }
}
