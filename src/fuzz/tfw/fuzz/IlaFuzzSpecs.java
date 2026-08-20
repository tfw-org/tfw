package tfw.fuzz;

import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.shortila.ShortIla;

import tfw.immutable.ilaf.booleanilaf.BooleanIlaFactoryFromArray;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactoryFromArray;
import tfw.immutable.ilaf.charilaf.CharIlaFactoryFromArray;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactoryFromArray;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactoryFromArray;
import tfw.immutable.ilaf.intilaf.IntIlaFactoryFromArray;
import tfw.immutable.ilaf.longilaf.LongIlaFactoryFromArray;
import tfw.immutable.ilaf.objectilaf.ObjectIlaFactoryFromArray;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactoryFromArray;

public final class IlaFuzzSpecs {

    private IlaFuzzSpecs() {
    }

    public static IlaFuzzSpec<
            boolean[],
            BooleanIla> booleanSpec() {

        return new IlaFuzzSpec<>(
                "BooleanIlaFactoryFromArray",
                new IlaArrayAdapter<>() {

                    @Override
                    public boolean[] create(int length) {
                        return new boolean[length];
                    }

                    @Override
                    public void initialize(
                            boolean[] array) {

                        for (int i = 0;
                             i < array.length;
                             i++) {
                            array[i] = (i & 1) != 0;
                        }
                    }

                    @Override
                    public boolean[] copy(
                            boolean[] array) {

                        return array.clone();
                    }

                    @Override
                    public void assertElementEquals(
                            boolean[] expected,
                            int expectedIndex,
                            boolean[] actual,
                            int actualIndex) {

                        if (expected[expectedIndex]
                                != actual[actualIndex]) {

                            throw new AssertionError(
                                    "expected="
                                            + expected[expectedIndex]
                                            + ", actual="
                                            + actual[actualIndex]);
                        }
                    }
                },
                array -> BooleanIlaFactoryFromArray
                        .create(array)
                        .create(),
                BooleanIla::length,
                BooleanIla::get);
    }

    public static IlaFuzzSpec<
            byte[],
            ByteIla> byteSpec() {

        return new IlaFuzzSpec<>(
                "ByteIlaFactoryFromArray",
                new IlaArrayAdapter<>() {

                    @Override
                    public byte[] create(int length) {
                        return new byte[length];
                    }

                    @Override
                    public void initialize(
                            byte[] array) {

                        for (int i = 0;
                             i < array.length;
                             i++) {
                            array[i] =
                                    (byte) (
                                            i * 37
                                                    + 11);
                        }
                    }

                    @Override
                    public byte[] copy(
                            byte[] array) {

                        return array.clone();
                    }

                    @Override
                    public void assertElementEquals(
                            byte[] expected,
                            int expectedIndex,
                            byte[] actual,
                            int actualIndex) {

                        if (expected[expectedIndex]
                                != actual[actualIndex]) {

                            throw new AssertionError(
                                    "expected="
                                            + expected[expectedIndex]
                                            + ", actual="
                                            + actual[actualIndex]);
                        }
                    }
                },
                array -> ByteIlaFactoryFromArray
                        .create(array)
                        .create(),
                ByteIla::length,
                ByteIla::get);
    }

    public static IlaFuzzSpec<
            char[],
            CharIla> charSpec() {

        return new IlaFuzzSpec<>(
                "CharIlaFactoryFromArray",
                new IlaArrayAdapter<>() {

                    @Override
                    public char[] create(int length) {
                        return new char[length];
                    }

                    @Override
                    public void initialize(
                            char[] array) {

                        for (int i = 0;
                             i < array.length;
                             i++) {

                            switch (i & 3) {
                                case 0:
                                    array[i] = '\0';
                                    break;

                                case 1:
                                    array[i] = '\uffff';
                                    break;

                                case 2:
                                    array[i] = (char) i;
                                    break;

                                default:
                                    array[i] =
                                            (char)
                                                    (0xffff - i);
                                    break;
                            }
                        }
                    }

                    @Override
                    public char[] copy(
                            char[] array) {

                        return array.clone();
                    }

                    @Override
                    public void assertElementEquals(
                            char[] expected,
                            int expectedIndex,
                            char[] actual,
                            int actualIndex) {

                        if (expected[expectedIndex]
                                != actual[actualIndex]) {

                            throw new AssertionError(
                                    "expected="
                                            + (int) expected[expectedIndex]
                                            + ", actual="
                                            + (int) actual[actualIndex]);
                        }
                    }
                },
                array -> CharIlaFactoryFromArray
                        .create(array)
                        .create(),
                CharIla::length,
                CharIla::get);
    }

    public static IlaFuzzSpec<
            double[],
            DoubleIla> doubleSpec() {

        return new IlaFuzzSpec<>(
                "DoubleIlaFactoryFromArray",
                new IlaArrayAdapter<>() {

                    @Override
                    public double[] create(int length) {
                        return new double[length];
                    }

                    @Override
                    public void initialize(
                            double[] array) {

                        for (int i = 0;
                             i < array.length;
                             i++) {

                            switch (i & 7) {
                                case 0:
                                    array[i] = 0.0;
                                    break;

                                case 1:
                                    array[i] = -0.0;
                                    break;

                                case 2:
                                    array[i] = Double.NaN;
                                    break;

                                case 3:
                                    array[i] =
                                            Double.POSITIVE_INFINITY;
                                    break;

                                case 4:
                                    array[i] =
                                            Double.NEGATIVE_INFINITY;
                                    break;

                                case 5:
                                    array[i] =
                                            Double.MIN_VALUE;
                                    break;

                                case 6:
                                    array[i] =
                                            Double.MAX_VALUE;
                                    break;

                                default:
                                    array[i] =
                                            i * 1.23456789;
                                    break;
                            }
                        }
                    }

                    @Override
                    public double[] copy(
                            double[] array) {

                        return array.clone();
                    }

                    @Override
                    public void assertElementEquals(
                            double[] expected,
                            int expectedIndex,
                            double[] actual,
                            int actualIndex) {

                        long expectedBits =
                                Double.doubleToRawLongBits(
                                        expected[expectedIndex]);

                        long actualBits =
                                Double.doubleToRawLongBits(
                                        actual[actualIndex]);

                        if (expectedBits != actualBits) {
                            throw new AssertionError(
                                    "expectedBits="
                                            + Long.toHexString(
                                                    expectedBits)
                                            + ", actualBits="
                                            + Long.toHexString(
                                                    actualBits));
                        }
                    }
                },
                array -> DoubleIlaFactoryFromArray
                        .create(array)
                        .create(),
                DoubleIla::length,
                DoubleIla::get);
    }

    public static IlaFuzzSpec<
            float[],
            FloatIla> floatSpec() {

        return new IlaFuzzSpec<>(
                "FloatIlaFactoryFromArray",
                new IlaArrayAdapter<>() {

                    @Override
                    public float[] create(int length) {
                        return new float[length];
                    }

                    @Override
                    public void initialize(
                            float[] array) {

                        for (int i = 0;
                             i < array.length;
                             i++) {

                            switch (i & 7) {
                                case 0:
                                    array[i] = 0.0f;
                                    break;

                                case 1:
                                    array[i] = -0.0f;
                                    break;

                                case 2:
                                    array[i] = Float.NaN;
                                    break;

                                case 3:
                                    array[i] =
                                            Float.POSITIVE_INFINITY;
                                    break;

                                case 4:
                                    array[i] =
                                            Float.NEGATIVE_INFINITY;
                                    break;

                                case 5:
                                    array[i] =
                                            Float.MIN_VALUE;
                                    break;

                                case 6:
                                    array[i] =
                                            Float.MAX_VALUE;
                                    break;

                                default:
                                    array[i] =
                                            i * 1.2345678f;
                                    break;
                            }
                        }
                    }

                    @Override
                    public float[] copy(
                            float[] array) {

                        return array.clone();
                    }

                    @Override
                    public void assertElementEquals(
                            float[] expected,
                            int expectedIndex,
                            float[] actual,
                            int actualIndex) {

                        int expectedBits =
                                Float.floatToRawIntBits(
                                        expected[expectedIndex]);

                        int actualBits =
                                Float.floatToRawIntBits(
                                        actual[actualIndex]);

                        if (expectedBits != actualBits) {
                            throw new AssertionError(
                                    "expectedBits="
                                            + Integer.toHexString(
                                                    expectedBits)
                                            + ", actualBits="
                                            + Integer.toHexString(
                                                    actualBits));
                        }
                    }
                },
                array -> FloatIlaFactoryFromArray
                        .create(array)
                        .create(),
                FloatIla::length,
                FloatIla::get);
    }

    public static IlaFuzzSpec<
            int[],
            IntIla> intSpec() {

        return new IlaFuzzSpec<>(
                "IntIlaFactoryFromArray",
                new IlaArrayAdapter<>() {

                    @Override
                    public int[] create(int length) {
                        return new int[length];
                    }

                    @Override
                    public void initialize(
                            int[] array) {

                        for (int i = 0;
                             i < array.length;
                             i++) {
                            array[i] =
                                    i * 0x9e3779b9
                                            ^ 0x12345678;
                        }
                    }

                    @Override
                    public int[] copy(
                            int[] array) {

                        return array.clone();
                    }

                    @Override
                    public void assertElementEquals(
                            int[] expected,
                            int expectedIndex,
                            int[] actual,
                            int actualIndex) {

                        if (expected[expectedIndex]
                                != actual[actualIndex]) {

                            throw new AssertionError(
                                    "expected="
                                            + expected[expectedIndex]
                                            + ", actual="
                                            + actual[actualIndex]);
                        }
                    }
                },
                array -> IntIlaFactoryFromArray
                        .create(array)
                        .create(),
                IntIla::length,
                IntIla::get);
    }

    public static IlaFuzzSpec<
            long[],
            LongIla> longSpec() {

        return new IlaFuzzSpec<>(
                "LongIlaFactoryFromArray",
                new IlaArrayAdapter<>() {

                    @Override
                    public long[] create(int length) {
                        return new long[length];
                    }

                    @Override
                    public void initialize(
                            long[] array) {

                        for (int i = 0;
                             i < array.length;
                             i++) {
                            array[i] =
                                    0x123456789ABCDEFL
                                            ^ ((long) i
                                            * 0x100000001L);
                        }
                    }

                    @Override
                    public long[] copy(
                            long[] array) {

                        return array.clone();
                    }

                    @Override
                    public void assertElementEquals(
                            long[] expected,
                            int expectedIndex,
                            long[] actual,
                            int actualIndex) {

                        if (expected[expectedIndex]
                                != actual[actualIndex]) {

                            throw new AssertionError(
                                    "expected="
                                            + expected[expectedIndex]
                                            + ", actual="
                                            + actual[actualIndex]);
                        }
                    }
                },
                array -> LongIlaFactoryFromArray
                        .create(array)
                        .create(),
                LongIla::length,
                LongIla::get);
    }

    public static IlaFuzzSpec<
            short[],
            ShortIla> shortSpec() {

        return new IlaFuzzSpec<>(
                "ShortIlaFactoryFromArray",
                new IlaArrayAdapter<>() {

                    @Override
                    public short[] create(int length) {
                        return new short[length];
                    }

                    @Override
                    public void initialize(
                            short[] array) {

                        for (int i = 0;
                             i < array.length;
                             i++) {
                            array[i] =
                                    (short)
                                            (i * 7919
                                                    + 12345);
                        }
                    }

                    @Override
                    public short[] copy(
                            short[] array) {

                        return array.clone();
                    }

                    @Override
                    public void assertElementEquals(
                            short[] expected,
                            int expectedIndex,
                            short[] actual,
                            int actualIndex) {

                        if (expected[expectedIndex]
                                != actual[actualIndex]) {

                            throw new AssertionError(
                                    "expected="
                                            + expected[expectedIndex]
                                            + ", actual="
                                            + actual[actualIndex]);
                        }
                    }
                },
                array -> ShortIlaFactoryFromArray
                        .create(array)
                        .create(),
                ShortIla::length,
                ShortIla::get);
    }

    public static IlaFuzzSpec<
            String[],
            ObjectIla<String>> objectSpec() {

        return new IlaFuzzSpec<>(
                "ObjectIlaFactoryFromArray",
                new IlaArrayAdapter<>() {

                    @Override
                    public String[] create(int length) {
                        return new String[length];
                    }

                    @Override
                    public void initialize(
                            String[] array) {

                        for (int i = 0;
                             i < array.length;
                             i++) {

                            switch (i & 3) {
                                case 0:
                                    array[i] = null;
                                    break;

                                case 1:
                                    array[i] =
                                            "tfw-" + i;
                                    break;

                                case 2:
                                    array[i] =
                                            "value-" + i
                                                    + "-distinct";
                                    break;

                                default:
                                    array[i] =
                                            String.valueOf(
                                                    Integer.MIN_VALUE
                                                            + i);
                                    break;
                            }
                        }
                    }

                    @Override
                    public String[] copy(
                            String[] array) {

                        return array.clone();
                    }

                    @Override
                    public void assertElementEquals(
                            String[] expected,
                            int expectedIndex,
                            String[] actual,
                            int actualIndex) {

                        String expectedValue =
                                expected[expectedIndex];

                        String actualValue =
                                actual[actualIndex];

                        if (expectedValue == null
                                ? actualValue != null
                                : !expectedValue.equals(
                                        actualValue)) {

                            throw new AssertionError(
                                    "expected="
                                            + expectedValue
                                            + ", actual="
                                            + actualValue);
                        }
                    }
                },
                array -> ObjectIlaFactoryFromArray
                        .<String>create(array)
                        .create(),
                ObjectIla::length,
                ObjectIla::get);
    }
}
