package tfw.immutable.ilm.doubleilm;

import tfw.check.Argument;
import tfw.dsp.xform.Ooura1D;
import tfw.immutable.DataInvalidException;

public class RealFftDoubleIlm {
	private RealFftDoubleIlm() {}
	
	public static DoubleIlm create(DoubleIlm doubleIlm, int fftSize) {
		Argument.assertNotNull(doubleIlm, "doubleIlm");
		// Check to make sure fftSize is a power of 2.
		
		return new MyDoubleIlm(doubleIlm, fftSize);
	}
	
	private static class MyDoubleIlm extends AbstractDoubleIlm {
		private final DoubleIlm doubleIlm;
		private final int fftSize;
		private final int dataWidth;
		private final double[] buffer;
		private final int[] ip;
		private final double[] w;
		
		public MyDoubleIlm(DoubleIlm doubleIlm, int fftSize) {
			super(fftSize, doubleIlm.height());
			
			this.doubleIlm = doubleIlm;
			this.fftSize = fftSize;
			this.dataWidth = (int)doubleIlm.width();
			this.buffer = new double[fftSize];
			this.ip = new int[2+(int)Math.ceil(Math.sqrt(fftSize / 2))];
			this.w = new double[fftSize / 2];
		}
	
		@Override
		protected void toArrayImpl(double[] array, int offset, int rowStride, int colStride, long rowStart,
				long colStart, int rowCount, int colCount) throws DataInvalidException {
			Argument.assertEquals(colStart, 0, "colStart", "0");
			Argument.assertEquals(colCount, fftSize, "colCount", "fftSize");
			
			if (colStride == 1) {
				for (int i=0 ; i < rowCount ; i++) {
					doubleIlm.toArray(buffer, 0, fftSize, 1, rowStart + i, 0, 1, dataWidth);
					Ooura1D.rdft(fftSize, 1, buffer, ip, w);
					
					System.arraycopy(buffer,  0,  array,  offset+(rowStride * i),  fftSize);
				}
			}
			else {
				for (int i=0 ; i < rowCount ; i++) {
					doubleIlm.toArray(buffer, 0, fftSize, 1, rowStart + i, 0, 1, dataWidth);
					Ooura1D.rdft(fftSize, 1, buffer, ip, w);
					
					int rowOffset = offset + (rowStride * i);
					for (int j=0 ; j < colCount ; j++) {
						array[rowOffset+(colStride * j)] = buffer[j];
					}
				}
			}
		}
	}
}
