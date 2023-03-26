package tfw.dsp.window;

import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

public final class BlackmanWindowDoubleIla
{
	private static final double A0 = 0.42;
	private static final double A1 = 0.5;
	private static final double A2 = 0.08;
	
	private BlackmanWindowDoubleIla() {}
	
	public static DoubleIla create(DoubleIla doubleIla, int windowLength,
		long numberOfWindows)
	{
		double[] window = new double[windowLength];
		
		double constant1 = 2.0 * Math.PI / (windowLength - 1);
		double constant2 = 2.0 * constant1;

		for (int i=0 ; i < windowLength ; i++)
		{
			window[i] = A0 - A1 * Math.cos(constant1 * i) +
				A2 * Math.cos(constant2 * i);
		}
		
		return(DoubleIlaFromArray.create(window));
	}
}