package tfw.immutable.ilm.doubleilm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaRamp;
import tfw.immutable.ila.doubleila.DoubleIlaUtil;

final class TakeSkipDoubleIlmTest {
    @Test
    void takeSkipDoubleIlmTest() throws Exception {
        DoubleIla doubleIla = DoubleIlaRamp.create(-10.0, 1.0, 20);
        double[] doubleArray = DoubleIlaUtil.toArray(doubleIla);

        DoubleIlm takeSkip1 = TakeSkipDoubleIlm.create(doubleIla, 10, 10);
        double[] takeSkip1Array = DoubleIlmUtil.toArray(takeSkip1);
        assertThat(doubleArray).isEqualTo(takeSkip1Array);
        assertThat(takeSkip1.width()).isEqualTo(10);
        assertThat(takeSkip1.height()).isEqualTo(2);

        DoubleIlm takeSkip2 = TakeSkipDoubleIlm.create(doubleIla, 4, 5);
        double[] takeSkip2Array = new double[16];
        System.arraycopy(doubleArray, 0, takeSkip2Array, 0, 4);
        System.arraycopy(doubleArray, 5, takeSkip2Array, 4, 4);
        System.arraycopy(doubleArray, 10, takeSkip2Array, 8, 4);
        System.arraycopy(doubleArray, 15, takeSkip2Array, 12, 4);
        assertThat(takeSkip2Array).isEqualTo(DoubleIlmUtil.toArray(takeSkip2));
        assertThat(takeSkip2.width()).isEqualTo(4);
        assertThat(takeSkip2.height()).isEqualTo(4);

        DoubleIlm takeSkip3 = TakeSkipDoubleIlm.create(doubleIla, 10, 5);
        double[] takeSkip3Array = new double[30];
        System.arraycopy(doubleArray, 0, takeSkip3Array, 0, 10);
        System.arraycopy(doubleArray, 5, takeSkip3Array, 10, 10);
        System.arraycopy(doubleArray, 10, takeSkip3Array, 20, 10);
        assertThat(takeSkip3Array).isEqualTo(DoubleIlmUtil.toArray(takeSkip3));
        assertThat(takeSkip3.width()).isEqualTo(10);
        assertThat(takeSkip3.height()).isEqualTo(3);

        DoubleIlm takeSkip4 = TakeSkipDoubleIlm.create(doubleIla, 10, 5);
        double[] takeSkip4Array = new double[9];
        System.arraycopy(doubleArray, 1, takeSkip4Array, 0, 3);
        System.arraycopy(doubleArray, 6, takeSkip4Array, 3, 3);
        System.arraycopy(doubleArray, 11, takeSkip4Array, 6, 3);
        assertThat(takeSkip4Array).isEqualTo(DoubleIlmUtil.toArray(takeSkip4, 0, 1, 3, 3));
        assertThat(takeSkip4.width()).isEqualTo(10);
        assertThat(takeSkip4.height()).isEqualTo(3);

        DoubleIlm takeSkip5 = TakeSkipDoubleIlm.create(doubleIla, 10, 5);
        StridedDoubleIlm stridedTakeSkip5 = StridedDoubleIlmFromDoubleIlm.create(takeSkip5, new double[0]);
        double[] takeSkip5Array = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, -9.0, 0.0, -8.0, 0.0, -7.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, -4.0, 0.0, -3.0, 0.0, -2.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 2.0, 0.0, 3.0
        };
        double[] takeSkip5Test = new double[36];
        stridedTakeSkip5.get(takeSkip5Test, 7, 12, 2, 0, 1, 3, 3);
        assertThat(takeSkip5Array).isEqualTo(takeSkip5Test);
        assertThat(stridedTakeSkip5.width()).isEqualTo(10);
        assertThat(stridedTakeSkip5.height()).isEqualTo(3);
    }
}
