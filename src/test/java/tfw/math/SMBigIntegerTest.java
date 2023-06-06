package tfw.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SMBigIntegerTest {
    @Test
    void testSMBigIntegerValue() {
        final SMBigInteger smBigInteger = new SMBigInteger();

        smBigInteger.setValue(Long.MIN_VALUE);
        assertEquals(Long.MIN_VALUE, smBigInteger.longValue());
        assertEquals(Long.MIN_VALUE, smBigInteger.boundedLongValue());
        smBigInteger.setValue(0);
        assertEquals(0L, smBigInteger.longValue());
        assertEquals(0L, smBigInteger.boundedLongValue());
        smBigInteger.setValue(Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, smBigInteger.longValue());
        assertEquals(Long.MAX_VALUE, smBigInteger.boundedLongValue());

        for (int i = 0; i < 63; i++) {
            long l = 1L << i;

            smBigInteger.setValue(l);
            assertEquals(l, smBigInteger.longValue());
            assertEquals(l, smBigInteger.boundedLongValue());

            smBigInteger.setValue(-l);
            assertEquals(l, smBigInteger.longValue());
            assertEquals(-l, smBigInteger.boundedLongValue());
        }
    }
}
