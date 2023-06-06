package tfw.immutable.ilm.doubleilm;

import java.util.Arrays;
import junit.framework.TestCase;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

public class SegmentDoubleIlmTest extends TestCase {
    public void testSegmentDoubleIlm() throws Exception {
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
        double[] test1Array = segment1.toArray();
        assertTrue(Arrays.equals(test1Check, test1Array));

        double[] test2Check = new double[] {
            4.0, 5.0, 6.0, 0.0, 0.0,
            5.0, 6.0, 7.0, 0.0, 0.0,
            6.0, 7.0, 8.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0
        };
        double[] test2Array = new double[25];
        DoubleIlm segment2 = SegmentDoubleIlm.create(doubleIlm, 2, 2, 3, 3);
        segment2.toArray(test2Array, 0, 5, 1, 0, 0, 3, 3);
        assertTrue(Arrays.equals(test2Check, test2Array));

        double[] test3Check = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 1.0, 2.0,
            0.0, 0.0, 1.0, 2.0, 3.0,
            0.0, 0.0, 2.0, 3.0, 4.0
        };
        double[] test3Array = new double[25];
        DoubleIlm segment3 = SegmentDoubleIlm.create(doubleIlm, 0, 0, 3, 3);
        segment3.toArray(test3Array, 12, 5, 1, 0, 0, 3, 3);
        assertTrue(Arrays.equals(test3Check, test3Array));

        double[] test4Check = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 2.0, 0.0, 3.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 3.0, 0.0, 4.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0
        };
        double[] test4Array = new double[25];
        DoubleIlm segment4 = SegmentDoubleIlm.create(doubleIlm, 1, 1, 3, 3);
        segment4.toArray(test4Array, 6, 10, 2, 0, 0, 2, 2);
        assertTrue(Arrays.equals(test4Check, test4Array));
    }
}
