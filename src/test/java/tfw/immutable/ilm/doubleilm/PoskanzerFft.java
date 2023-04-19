/* libfft.c - fast fourier transform library
 *
 * Copyright (C) 1989 by Jef Poskanzer.
 * 
 * Permission to use, copy, modify, and distribute this software and its
 * documentation for any purpose and without fee is hereby granted, provided
 * that the above copyright notice appear in all copies and that both  that
 * copyright notice and this permission notice appear in supporting
 * documentation. This software is provided "as is" without express or
 * implied warranty.
 */
package tfw.immutable.ilm.doubleilm;

import java.util.Arrays;

/**
 * This is a Java implementation of the fast Fourier transform
 * written by Jef Poskanzer. The copyright appears above.
 */
public class PoskanzerFft {
	private static final double TWOPI = 2.0 * Math.PI;
	
	// Limits on the number of bits this algorithm can utilize
	private static final int LOG2_MAXFFTSIZE = 15;
	private static final int LOG2_MINFFTSIZE = 5;
	private static final int MAXFFTSIZE = 1 << LOG2_MAXFFTSIZE;
	
	// Private class data
	private boolean applyHammWindow;
	private int fftSize;
	private int size;
	private int tableIndx;
	private double tableSize;
	private int[] bitreverse;
	private int[] indxTable;
	private double[] cosTable;
	private double[] sinTable;
	private double[] hammWindow;
	private double[] iHammWindow;
	private boolean setFftSizeCalled = false;
	private int setFftSizeValue = 0;
	
	/**
	 * FFT class constructor
	 * Initializes code for doing a fast fourier transform.
	 * 
	 * @param size the size is a power of two such that 2^b is the number of samples
	 */
	public PoskanzerFft(int size) {
		this.size = size;
		
		applyHammWindow = false;
		setFftSizeCalled = true;
		setFftSizeValue = size;
	}
	
	/**
	 * A fast Fourier transform routine to rectangular format. When converting
	 * to the frequency domain, a Hamm window can be applied first to taper the
	 * ends of the audio signal. This reduces the distortion introduced by
	 * the FFT dealing with abrupt ends. The side effect of this is that when
	 * the FFT is inverted back to the time domain, the ends of the audio
	 * signal are tapered resulting in a pulsing effect when the audio is
	 * listened to. To eliminate this, after the audio is converted back to
	 * the time domain, an inverse Hamm window is applied to restore the
	 * correct amplitude of the samples at both ends of the signal.
	 * 
	 * The indxTable, cosTable and sinTable are look up tables containing
	 * values that are calculated with the FFT independent of the unique
	 * audio data. Since these values are always the same for a given FFT size,
	 * these tables are generate at the time the FFT size is set so there
	 * is no need to recalculate these values everytime the FFT is performed.
	 * The tables are created and populated in setFftSize().
	 * 
	 * @param real the double array which is the real part of the data to be transformed
	 * @param imaginary the double array which is the imaginary part of the
	 * data to be transformed (normally zero unless inverse transform is effected).
	 * @param invFlag the boolean value representing if inverse transform is being
	 * applied. true if inverse transform is being applied, false for a forward transform.
	 */
	public void doFft(double[] real, double[] imaginary, boolean invFlag) {
		int audioSample;
		double cos;
		int indx1;
		int indx2;
		int kn2;
		int n;
		int n2;
		double sin;
		double tr;
		double ti;
		
		if (setFftSizeCalled)
		{
			realSetFftSize(setFftSizeValue);
			setFftSizeCalled = false;
		}
		
		n2 = (n = (1 << fftSize)) / 2;
		
		// Before converting from time to frequency domain, apply
		// the Hamm window to the audio data to get a cleaner FFT.
		if ((invFlag == false) && (applyHammWindow == true)) {
			for (indx1=0 ; (indx1 < real.length) && (indx1 < hammWindow.length); ++ indx1) {
				real[indx1] *= hammWindow[indx1];
			}
		}
		else if (invFlag == true) {
			// Before converting back to the time domain, mirror the
			// real and imaginary into the upper symmetrical halves.
			real[real.length / 2] = real[0];
			imaginary[imaginary.length / 2] = imaginary[0];
			
			for (indx1=1 ; indx1 < (real.length / 2) ; ++indx1) {
				real[real.length - indx1] = real[indx1];
				imaginary[imaginary.length - indx1] = -imaginary[indx1];
			}
		}
		
		tableIndx = 0;
		
		for (audioSample=0 ; audioSample < fftSize ; ++audioSample) {
			for (indx1=0 ; indx1 < n ; indx1 += n2) {
				for (indx2=0 ; indx2 < n2 ; ++indx2,++indx1) {
					cos = cosTable[tableIndx];
					sin = sinTable[tableIndx];
					kn2 = indxTable[tableIndx];
					
					++tableIndx;
					
					if (invFlag) {
						sin = -sin;
					}
					
					tr = (real[kn2] * cos) + (imaginary[kn2] * sin);
					ti = (imaginary[kn2] * cos) - (real[kn2] * sin);
					
					real[kn2] = real[indx1] - tr;
					imaginary[kn2] = imaginary[indx1] - ti;
					real[indx1] += tr;
					imaginary[indx1] += ti;
				}
			}
			
			n2 /= 2;
		}
		
		for (indx1=0 ; indx1 < n ; ++indx1) {
			if ((indx2 = bitreverse[indx1]) <= indx1) {
				continue;
			}
			
			tr = real[indx1];
			ti = imaginary[indx1];
			
			real[indx1] = real[indx2];
			imaginary[indx1] = imaginary[indx2];
			real[indx2] = tr;
			imaginary[indx2] = ti;
		}
		
		// Finally, multiply each value by 1/n, if this is the forward transform.
		if (!invFlag) {
			double f = 1.0 / n;
			
			for (indx1=0 ; indx1 < n ; ++indx1) {
				real[indx1] *= f;
				imaginary[indx1] *= f;
			}
		}
		
		// After converting back to the time domain, apply the
		// inverse Hamm window to restore a smooth chunk of
		// audio that isn't tapered at either end.
		if ((invFlag == true) && (applyHammWindow == true)) {
			for (indx1=0 ; (indx1 < real.length)&& (indx1 < iHammWindow.length); ++indx1) {
				real[indx1] *= iHammWindow[indx1];
			}
		}
	}
	
	/**
	 * Used to turn the Hamm windowing on or off.
	 * 
	 * The size of the hamm window is specified in the second argument. If
	 * the size specified is less than or equal to zero, then the size of
	 * the window is equal to the length of the pre-fft real array.
	 * 
	 * @param hammFlag boolean true turns window on, false turns it off.
	 */
	public void doHammWindow(boolean hammFlag, int size) {
		applyHammWindow = hammFlag;
		
		if (applyHammWindow) {
			if (size <= 0) {
				size = this.size;
			}
			
			hammWindow = new double[size];
			iHammWindow = new double[size];
			
			for (int indx=0 ; indx < hammWindow.length ; ++indx) {
				hammWindow[indx] = 0.54 - (0.46 * Math.cos((2 * Math.PI * indx) / hammWindow.length));
				iHammWindow[indx] = 1 / hammWindow[indx];
			}
		}
	}
	
	/**
	 * Convert polar format pairs to rectangular format pairs.
	 * 
	 * @param real is the magnitudes of the bins in the spectrum.
	 * @param imaginary is the phase of the bins in the spectrum.
	 */
	public static void polarToRec(double[] real, double[] imaginary) {
		polarToRec(real, imaginary, real.length);
	}
	
	/**
	 * Convert polar format pairs to rectangular format pairs.
	 * 
	 * @param real is the magnitudes of the bins in the spectrum.
	 * @param imaginary is the phase of the bins in the spectrum.
	 * @param size the number of the FFT magnitude & phase paris to convert to rectangular format.
	 */
	public static void polarToRec(double[] real, double[] imaginary, int size) {
		int indx;
		double magnitude;
		double phase;
		
		for (indx=0 ; indx < size ; ++indx) {
			magnitude = real[indx];
			phase = imaginary[indx];
			
			real[indx] = magnitude * Math.cos(phase);
			imaginary[indx] = magnitude * Math.sin(phase);
		}
	}
	
	/**
	 * Convert rectangular format pairs to polar format pairs.
	 * 
	 * @param real contains the real portions of the complex number pairs in the spectrum.
	 * @param imaginary contains the imaginary portions of the complex number pairs in the spectrum.
	 */
	public static void recToPolar(double[] real, double[] imaginary) {
		recToPolar(real, imaginary, real.length);
	}
	
	/**
	 * Convert rectangular format pairs to polar format pairs.
	 * 
	 * @param real contains the real portions of the complex number pairs in the spectrum.
	 * @param imaginary contains the imaginary portions of the complex number pairs in the spectrum.
	 * @param size the size being used.
	 */
	public static void recToPolar(double[] real, double[] imaginary, int size) {
		int indx;
		double magnitude;
		double phase;
		
		for (indx=0 ; indx < size ; ++indx) {
			magnitude = real[indx];
			phase = imaginary[indx];
			
			real[indx] = Math.sqrt((magnitude * magnitude) + (phase * phase));
			imaginary[indx] = Math.atan2(phase, magnitude);
		}
	}
	
	public void setFftSize(int size) {
		setFftSizeCalled = true;
		setFftSizeValue = size;
	}
	
	/**
	 * setFftSize allows the this class to be used to perform
	 * fast fourier transforms of different lengths. The FFT
	 * requires a lot of processing. Some calculations are made
	 * in this method, once the size is set for this FFT object.
	 * these calculations are performed and the results are
	 * stored in look up tables. Saving these values in look up
	 * tables increases the speed and efficiency of the FFT.
	 * 
	 * @param size the size of the FFT as an exponent of 2
	 * eg. an fftSize of 10 specifies an FFT size of 1024.
	 */
	private void realSetFftSize(int size) {
		double ang;
		int audioSample;
		int indx1;
		int indx2;
		int n;
		int n2;
		int reverseBitOrder;
		
		if (bitreverse == null) {
			bitreverse = new int[MAXFFTSIZE];
		}
		
		fftSize = 0;
		
		while (size > 1) {
			size /= 2;
			++fftSize;
		}
		
		if (fftSize > LOG2_MAXFFTSIZE) {
			fftSize = LOG2_MAXFFTSIZE;
		}
		else if (fftSize < LOG2_MINFFTSIZE) {
			fftSize = LOG2_MINFFTSIZE;
		}
		
		tableSize = (1 << fftSize) * fftSize * 0.5;
		
		indxTable = new int[(int)tableSize];
		cosTable = new double[(int)tableSize];
		sinTable = new double[(int)tableSize];
		
		Arrays.fill(indxTable, 0);
		Arrays.fill(cosTable, 0.0);
		Arrays.fill(sinTable, 0.0);
		
		for (indx1=(1 << fftSize)-1 ; indx1 >= 0 ; --indx1) {
			reverseBitOrder = 0;
			
			for (indx2=0 ; indx2 < fftSize ; ++indx2) {
				reverseBitOrder *= 2;
				
				if ((indx1 & (1 << indx2)) != 0) {
					++reverseBitOrder;
				}
			}
			
			bitreverse[indx1] = reverseBitOrder;
		}
		
		n2 = (n = (1 << fftSize)) / 2;
		
		tableIndx = 0;
		
		for (audioSample=0 ; audioSample < fftSize ; ++audioSample) {
			for (indx1=0 ; indx1 < n ; indx1 += n2) {
				for (indx2=0 ; indx2 < n2 ; ++indx2,++indx1) {
					ang = (TWOPI * bitreverse[indx1 / n2]) / n;
					cosTable[tableIndx] = Math.cos(ang);
					sinTable[tableIndx] = Math.sin(ang);
					indxTable[tableIndx] = indx1 + n2;
					
					++tableIndx;
				}
			}
			
			n2 /= 2;
		}
	}
}
