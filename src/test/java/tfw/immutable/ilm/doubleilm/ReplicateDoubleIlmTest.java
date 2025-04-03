package tfw.immutable.ilm.doubleilm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

final class ReplicateDoubleIlmTest {
    @Test
    void replicateDoubleIlmTest() throws Exception {
        DoubleIla doubleIla = DoubleIlaFromArray.create(new double[] {0.0, 1.0, 2.0, 3.0, 4.0});

        DoubleIlm replicate = ReplicateDoubleIlm.create(doubleIla, 5);
        StridedDoubleIlm stridedReplicate = StridedDoubleIlmFromDoubleIlm.create(replicate, new double[0]);
        double[] test1Check = new double[] {
            0.0, 1.0, 2.0, 3.0, 4.0,
            0.0, 1.0, 2.0, 3.0, 4.0,
            0.0, 1.0, 2.0, 3.0, 4.0,
            0.0, 1.0, 2.0, 3.0, 4.0,
            0.0, 1.0, 2.0, 3.0, 4.0
        };
        double[] test1 = DoubleIlmUtil.toArray(replicate);
        assertThat(test1Check).isEqualTo(test1);

        double[] test2Check = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 2.0, 3.0,
            0.0, 0.0, 1.0, 2.0, 3.0,
            0.0, 0.0, 1.0, 2.0, 3.0
        };
        double[] test2 = new double[25];
        stridedReplicate.get(test2, 12, 5, 1, 1, 1, 3, 3);
        assertThat(test2Check).isEqualTo(test2);

        double[] test3Check = new double[] {
            2.0, 3.0, 4.0, 0.0, 0.0,
            2.0, 3.0, 4.0, 0.0, 0.0,
            2.0, 3.0, 4.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0
        };
        double[] test3 = new double[25];
        stridedReplicate.get(test3, 0, 5, 1, 2, 2, 3, 3);
        assertThat(test3Check).isEqualTo(test3);

        double[] test4Check = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 2.0, 0.0, 3.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 2.0, 0.0, 3.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0
        };
        double[] test4 = new double[25];
        stridedReplicate.get(test4, 6, 10, 2, 2, 2, 2, 2);
        assertThat(test4Check).isEqualTo(test4);
    }
}
