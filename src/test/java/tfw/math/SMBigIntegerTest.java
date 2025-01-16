package tfw.math;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class SMBigIntegerTest {
    @Test
    void smBigIntegerValueTest() {
        final SMBigInteger smBigInteger = new SMBigInteger();

        smBigInteger.setValue(Long.MIN_VALUE);
        assertThat(Long.MIN_VALUE).isEqualTo(smBigInteger.longValue());
        assertThat(Long.MIN_VALUE).isEqualTo(smBigInteger.boundedLongValue());
        smBigInteger.setValue(0);
        assertThat(0L).isEqualTo(smBigInteger.longValue());
        assertThat(0L).isEqualTo(smBigInteger.boundedLongValue());
        smBigInteger.setValue(Long.MAX_VALUE);
        assertThat(Long.MAX_VALUE).isEqualTo(smBigInteger.longValue());
        assertThat(Long.MAX_VALUE).isEqualTo(smBigInteger.boundedLongValue());

        for (int i = 0; i < 63; i++) {
            long l = 1L << i;

            smBigInteger.setValue(l);
            assertThat(l).isEqualTo(smBigInteger.longValue());
            assertThat(l).isEqualTo(smBigInteger.boundedLongValue());

            smBigInteger.setValue(-l);
            assertThat(l).isEqualTo(smBigInteger.longValue());
            assertThat(-l).isEqualTo(smBigInteger.boundedLongValue());
        }
    }
}
