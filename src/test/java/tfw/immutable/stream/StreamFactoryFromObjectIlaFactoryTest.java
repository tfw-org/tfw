package tfw.immutable.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ilaf.objectilaf.ObjectIlaFactory;
import tfw.immutable.ilaf.objectilaf.ObjectIlaFactoryFromArray;

final class StreamFactoryFromObjectIlaFactoryTest {
    @Test
    void successTest() throws IOException {
        final String[] array = new String[] {"A", "B", "C", "D"};
        final ObjectIlaFactory<String> o = ObjectIlaFactoryFromArray.create(array);
        final StreamFactory<String> sf = StreamFactoryFromObjectIlaFactory.create(o, String.class);
        final Stream<String> s = sf.create();
        final List<String> list = s.collect(Collectors.toList());

        assertThat(list.toArray()).isEqualTo(array);
    }

    @Test
    void badIlaLengthTest() {
        ObjectIlaFactory<Object> o = new ObjectIlaFactory<Object>() {
            @Override
            public ObjectIla<Object> create() {
                return new ObjectIla<Object>() {
                    @Override
                    public long length() throws IOException {
                        throw new IOException("Test ObjectIla that only throws IOExceptions!");
                    }

                    @Override
                    public void get(Object[] array, int arrayOffset, long ilaStart, int length) throws IOException {
                        throw new IOException("Test ObjectIla that only throws IOExceptions!");
                    }
                };
            }
        };
        final StreamFactory<Object> osf = StreamFactoryFromObjectIlaFactory.create(o, Object.class);

        assertThatThrownBy(osf::create)
                .isInstanceOf(IOException.class)
                .hasMessage("Test ObjectIla that only throws IOExceptions!");
    }

    @Test
    void badIlaGetTest() throws IOException {
        ObjectIlaFactory<String> o = new ObjectIlaFactory<String>() {
            @Override
            public ObjectIla<String> create() {
                return new ObjectIla<String>() {
                    @Override
                    public long length() throws IOException {
                        return 5;
                    }

                    @Override
                    public void get(String[] array, int arrayOffset, long ilaStart, int length) throws IOException {
                        throw new IOException("Test DoubleIla whose get() only throws IOExceptions!");
                    }
                };
            }
        };
        final StreamFactory<String> osf = StreamFactoryFromObjectIlaFactory.create(o, String.class);
        final Stream<String> s = osf.create();

        assertThatThrownBy(s::toArray)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("End size 0 is less than fixed size 5");
    }
}
