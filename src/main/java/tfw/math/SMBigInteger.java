package tfw.math;

public class SMBigInteger extends SignedMutableBigInteger {
    private static final SMBigInteger TWO_SMBIGINTEGER = new SMBigInteger();

    static {
        TWO_SMBIGINTEGER.setValue(2);
    }

    // Based on private constructor BigInteger(long).
    public void setValue(long value) {
        if (value < 0) {
            sign = -1;
            value = -value;
        } else {
            sign = 1;
        }
        ensureCapacity(2);

        int highWord = (int) (value >>> 32);

        if (highWord == 0) {
            this.value[0] = (int) value;
            this.intLen = 1;
        } else {
            this.value[0] = highWord;
            this.value[1] = (int) value;
            this.intLen = 2;
        }

        this.offset = 0;
    }

    // Based on BigInteger.longValue().
    public long longValue() {
        long result = 0;

        for (int i = offset + intLen - 1, p = 0; i >= offset; i--, p++) {
            result += (this.value[i] & BigInteger.LONG_MASK) << 32 * p;
        }

        return result;
    }

    public long boundedLongValue() {
        long longValue = longValue();

        if (intLen > 2) {
            if (sign == 1) {
                return Long.MAX_VALUE;
            } else {
                return Long.MIN_VALUE;
            }
        } else if (intLen == 2) {
            boolean unsignedLongCompare = (longValue + Long.MIN_VALUE) > (Long.MAX_VALUE + Long.MIN_VALUE);

            if (sign == 1 && unsignedLongCompare) {
                return Long.MAX_VALUE;
            } else if (sign == -1 && unsignedLongCompare) {
                return Long.MIN_VALUE;
            }
        }

        return sign * longValue;
    }

    public void signedDivide(SMBigInteger divisor, SMBigInteger quotient, SMBigInteger remainder) {
        if (divisor.boundedLongValue() == 0) {
            throw new ArithmeticException("/ by zero");
        }

        quotient.sign = sign * divisor.sign;

        MutableBigInteger mbi = divide(divisor, quotient, remainder != null);

        if (remainder != null) {
            remainder.sign = quotient.sign;
            remainder.copyValue(mbi);
        }
    }

    public void signedDivide(SMBigInteger divisor, SMBigInteger quotient, RoundingMode roundingMode) {
        final SMBigInteger buffer1 = new SMBigInteger();
        final SMBigInteger buffer2 = new SMBigInteger();

        signedDivide(divisor, quotient, buffer1);

        if (buffer1.isZero()) {
            return;
        }

        switch (roundingMode) {
            case UNNECESSARY:
                return;
            case DOWN:
                return;
            case UP:
                if (quotient.sign == 1) {
                    quotient.setValue(quotient.boundedLongValue() + 1);
                } else {
                    quotient.setValue(quotient.boundedLongValue() - 1);
                }
                return;
            case CEILING:
                if (quotient.sign == 1) {
                    quotient.setValue(quotient.boundedLongValue() + 1);
                }
                return;
            case FLOOR:
                quotient.setValue(quotient.boundedLongValue() - 1);
                return;
            case HALF_UP:
            case HALF_DOWN:
            case HALF_EVEN:
            default:
                break;
        }

        buffer1.signedMultiply(TWO_SMBIGINTEGER, buffer2);
        buffer2.sign = divisor.sign;
        buffer2.signedSubtract(divisor);

        switch (roundingMode) {
            case HALF_DOWN:
                if (buffer2.sign == 1) {
                    quotient.setValue(quotient.boundedLongValue() + quotient.sign);
                }
                break;
            case HALF_EVEN:
                if (buffer2.sign == 1 || buffer2.sign == 0 && quotient.isOdd()) {
                    quotient.setValue(quotient.boundedLongValue() + quotient.sign);
                }
                break;
            case HALF_UP:
                if (buffer2.sign != -1) {
                    quotient.setValue(quotient.boundedLongValue() + quotient.sign);
                }
                break;
            case CEILING:
            case DOWN:
            case FLOOR:
            case UNNECESSARY:
            case UP:
            default:
                break;
        }
    }

    public void signedMultiply(SMBigInteger right, SMBigInteger result) {
        result.sign = sign * right.sign;

        multiply(right, result);
    }
}
