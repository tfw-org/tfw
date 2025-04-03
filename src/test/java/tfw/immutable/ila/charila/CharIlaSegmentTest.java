package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class CharIlaSegmentTest {
    @Test
    void argumentsTest() throws Exception {
        final CharIla ila = CharIlaFromArray.create(new char[10]);
        final long ilaLength = ila.length();

        assertThatThrownBy(() -> CharIlaSegment.create(null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> CharIlaSegment.create(ila, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> CharIlaSegment.create(ila, ilaLength + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> CharIlaSegment.create(null, 0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> CharIlaSegment.create(ila, -1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> CharIlaSegment.create(ila, 0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> CharIlaSegment.create(ila, ilaLength + 1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start + length (=11) > ila.length() (=10) not allowed!");
        assertThatThrownBy(() -> CharIlaSegment.create(ila, 0, ilaLength + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start + length (=11) > ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] master = new char[length];
        for (int ii = 0; ii < master.length; ++ii) {
            master[ii] = (char) random.nextInt();
        }
        CharIla masterIla = CharIlaFromArray.create(master);
        CharIla checkIla = CharIlaSegment.create(masterIla, 0, masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final char epsilon = (char) 0;
        CharIlaCheck.checkWithoutCorrectness(checkIla, offsetLength, epsilon);
        for (long start = 0; start < length; ++start) {
            for (long len = 0; len < length - start; ++len) {
                char[] array = new char[(int) len];
                for (int ii = 0; ii < array.length; ++ii) {
                    array[ii] = master[ii + (int) start];
                }
                CharIla targetIla = CharIlaFromArray.create(array);
                CharIla actualIla = CharIlaSegment.create(masterIla, start, len);
                CharIlaCheck.checkCorrectness(targetIla, actualIla, offsetLength, maxStride, epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
