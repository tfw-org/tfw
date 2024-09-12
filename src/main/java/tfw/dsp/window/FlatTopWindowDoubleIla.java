package tfw.dsp.window;

import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

public final class FlatTopWindowDoubleIla {
    private static final double A0 = 1.0;
    private static final double A1 = 1.93;
    private static final double A2 = 1.29;
    private static final double A3 = 0.388;
    private static final double A4 = 0.322;

    private FlatTopWindowDoubleIla() {}

    public static DoubleIla create(DoubleIla doubleIla, int windowLength, long numberOfWindows) {
        double[] window = new double[windowLength];

        double constant1 = 2.0 * Math.PI / (windowLength - 1);
        double constant2 = 2.0 * constant1;
        double constant3 = 2.0 * constant2;
        double constant4 = 2.0 * constant3;

        for (int i = 0; i < windowLength; i++) {
            window[i] = A0
                    - A1 * Math.cos(constant1 * i)
                    + A2 * Math.cos(constant2 * i)
                    - A3 * Math.cos(constant3 * i)
                    + A4 * Math.cos(constant4 * i);
        }

        return DoubleIlaFromArray.create(window);
    }
}
