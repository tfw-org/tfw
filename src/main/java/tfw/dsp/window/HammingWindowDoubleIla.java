package tfw.dsp.window;

import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

public final class HammingWindowDoubleIla {
    private static final double A0 = 25.0 / 46.0;
    private static final double A1 = 21.0 / 46.0;

    private HammingWindowDoubleIla() {}

    public static DoubleIla create(DoubleIla doubleIla, int windowLength, long numberOfWindows) {
        double[] window = new double[windowLength];

        double constant = 2.0 * Math.PI / (windowLength - 1);

        for (int i = 0; i < windowLength; i++) {
            window[i] = A0 - A1 * Math.cos(constant * i);
        }

        return (DoubleIlaFromArray.create(window));
    }
}
