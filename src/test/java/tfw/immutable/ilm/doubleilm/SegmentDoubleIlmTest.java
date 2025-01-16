package tfw.immutable.ilm.doubleilm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

final class SegmentDoubleIlmTest {
    @Test
    void segmentDoubleIlmTest() throws Exception {
        DoubleIla doubleIla = DoubleIlaFromArray.create(new double[] {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0});
        DoubleIlm doubleIlm = TakeSkipDoubleIlm.create(doubleIla, 5, 1);

        double[] test1Check = new double[] {
            0.0, 1.0, 2.0, 3.0, 4.0,
            1.0, 2.0, 3.0, 4.0, 5.0,
            2.0, 3.0, 4.0, 5.0, 6.0,
            3.0, 4.0, 5.0, 6.0, 7.0,
            4.0, 5.0, 6.0, 7.0, 8.0
        };
        DoubleIlm segment1 = SegmentDoubleIlm.create(doubleIlm, 0, 0, 5, 5);
        double[] test1Array = DoubleIlmUtil.toArray(segment1);
        assertThat(test1Check).isEqualTo(test1Array);

        double[] test2Check = new double[] {
            4.0, 5.0, 6.0, 0.0, 0.0,
            5.0, 6.0, 7.0, 0.0, 0.0,
            6.0, 7.0, 8.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0
        };
        double[] test2Array = new double[25];
        DoubleIlm segment2 = SegmentDoubleIlm.create(doubleIlm, 2, 2, 3, 3);
        StridedDoubleIlm stridedSegment2 = StridedDoubleIlmFromDoubleIlm.create(segment2, new double[0]);
        stridedSegment2.get(test2Array, 0, 5, 1, 0, 0, 3, 3);
        assertThat(test2Check).isEqualTo(test2Array);

        double[] test3Check = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 1.0, 2.0,
            0.0, 0.0, 1.0, 2.0, 3.0,
            0.0, 0.0, 2.0, 3.0, 4.0
        };
        double[] test3Array = new double[25];
        DoubleIlm segment3 = SegmentDoubleIlm.create(doubleIlm, 0, 0, 3, 3);
        StridedDoubleIlm stridedSegment3 = StridedDoubleIlmFromDoubleIlm.create(segment3, new double[0]);
        stridedSegment3.get(test3Array, 12, 5, 1, 0, 0, 3, 3);
        assertThat(test3Check).isEqualTo(test3Array);

        double[] test4Check = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 2.0, 0.0, 3.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 3.0, 0.0, 4.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0
        };
        double[] test4Array = new double[25];
        DoubleIlm segment4 = SegmentDoubleIlm.create(doubleIlm, 1, 1, 3, 3);
        StridedDoubleIlm stridedSegment4 = StridedDoubleIlmFromDoubleIlm.create(segment4, new double[0]);
        stridedSegment4.get(test4Array, 6, 10, 2, 0, 0, 2, 2);
        assertThat(test4Check).isEqualTo(test4Array);
    }
}
