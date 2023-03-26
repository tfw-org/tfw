package tfw.dsp.window;

import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

public final class BlackmanNuttallWindowDoubleIla
{
	private static final double A0 = 0.3635819;
	private static final double A1 = 0.4891775;
	private static final double A2 = 0.1365995;
	private static final double A3 = 0.0106411;
	
	private BlackmanNuttallWindowDoubleIla() {}
	
	public static DoubleIla create(DoubleIla doubleIla, int windowLength,
		long numberOfWindows)
	{
		double[] window = new double[windowLength];
		
		double constant1 = 2.0 * Math.PI / (windowLength - 1);
		double constant2 = 2.0 * constant1;
		double constant3 = 2.0 * constant2;

		for (int i=0 ; i < windowLength ; i++)
		{
			window[i] = A0 - A1 * Math.cos(constant1 * i) +
				A2 * Math.cos(constant2 * i) - A3 * Math.cos(constant3 * i);
		}
		
		return(DoubleIlaFromArray.create(window));
	}
}