package tfw.dsp.window;

import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

public final class HanningWindowDoubleIla
{
	private static final double A0 = 0.5;
	private static final double A1 = 0.5;
	
	private HanningWindowDoubleIla() {}
	
	public static DoubleIla create(int windowLength)
	{
		double[] window = new double[windowLength];
		
		double constant = 2.0 * Math.PI / (windowLength - 1);

		for (int i=0 ; i < windowLength ; i++)
		{
			window[i] = A0 - A1 * Math.cos(constant * i);
		}
		
		return(DoubleIlaFromArray.create(window));
	}
}