package tfw.immutable.ilm.doubleilm;

import java.util.Arrays;
import junit.framework.TestCase;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

public class ReplicateDoubleIlmTest extends TestCase {
    public void testReplicateDoubleIlm() throws Exception {
        DoubleIla doubleIla = DoubleIlaFromArray.create(new double[] {0.0, 1.0, 2.0, 3.0, 4.0});

        DoubleIlm replicate = ReplicateDoubleIlm.create(doubleIla, 5);
        double[] test1Check = new double[] {
            0.0, 1.0, 2.0, 3.0, 4.0,
            0.0, 1.0, 2.0, 3.0, 4.0,
            0.0, 1.0, 2.0, 3.0, 4.0,
            0.0, 1.0, 2.0, 3.0, 4.0,
            0.0, 1.0, 2.0, 3.0, 4.0
        };
        double[] test1 = replicate.toArray();
        assertTrue(Arrays.equals(test1Check, test1));

        double[] test2Check = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 2.0, 3.0,
            0.0, 0.0, 1.0, 2.0, 3.0,
            0.0, 0.0, 1.0, 2.0, 3.0
        };
        double[] test2 = new double[25];
        replicate.toArray(test2, 12, 5, 1, 1, 1, 3, 3);
        assertTrue(Arrays.equals(test2Check, test2));

        double[] test3Check = new double[] {
            2.0, 3.0, 4.0, 0.0, 0.0,
            2.0, 3.0, 4.0, 0.0, 0.0,
            2.0, 3.0, 4.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0
        };
        double[] test3 = new double[25];
        replicate.toArray(test3, 0, 5, 1, 2, 2, 3, 3);
        assertTrue(Arrays.equals(test3Check, test3));

        double[] test4Check = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 2.0, 0.0, 3.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 2.0, 0.0, 3.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0
        };
        double[] test4 = new double[25];
        replicate.toArray(test4, 6, 10, 2, 2, 2, 2, 2);
        assertTrue(Arrays.equals(test4Check, test4));
    }
}
