package tfw.immutable.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.stream.DoubleStream;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactoryFromArray;

final class DoubleStreamFactoryFromDoubleIlaFactoryTest {
    @Test
    void successTest() throws IOException {
        final double[] array = new double[] {-2.0, -1.0, 0.0, 1.0, 2.0};
        final DoubleIlaFactory i = DoubleIlaFactoryFromArray.create(array);
        final DoubleStreamFactory dsf = DoubleStreamFactoryFromDoubleIlaFactory.create(i);
        final DoubleStream s = dsf.create();
        final double[] list = s.toArray();

        assertThat(list).isEqualTo(array);
    }

    @Test
    void badIlaLengthTest() throws IOException {
        DoubleIlaFactory d = new DoubleIlaFactory() {
            @Override
            public DoubleIla create() {
                return new DoubleIla() {
                    @Override
                    public long length() throws IOException {
                        throw new IOException("Test DoubleIla that only throws IOExceptions!");
                    }

                    @Override
                    public void get(double[] array, int arrayOffset, long ilaStart, int length) throws IOException {
                        throw new IOException("Test DoubleIla that only throws IOExceptions!");
                    }
                };
            }
        };
        final DoubleStreamFactory dsf = DoubleStreamFactoryFromDoubleIlaFactory.create(d);

        assertThatThrownBy(() -> dsf.create())
                .isInstanceOf(IOException.class)
                .hasMessage("Test DoubleIla that only throws IOExceptions!");
    }

    @Test
    void badIlaGetTest() throws IOException {
        DoubleIlaFactory l = new DoubleIlaFactory() {
            @Override
            public DoubleIla create() {
                return new DoubleIla() {
                    @Override
                    public long length() throws IOException {
                        return 5;
                    }

                    @Override
                    public void get(double[] array, int arrayOffset, long ilaStart, int length) throws IOException {
                        throw new IOException("Test DoubleIla whose get() only throws IOExceptions!");
                    }
                };
            }
        };
        final DoubleStreamFactory dsf = DoubleStreamFactoryFromDoubleIlaFactory.create(l);
        final DoubleStream s = dsf.create();

        assertThatThrownBy(() -> s.toArray())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("End size 0 is less than fixed size 5");
    }
}
