package tfw.immutable.ilm.doubleilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.doubleila.DoubleIla;

public class ReplicateDoubleIlm {
	private ReplicateDoubleIlm() {}
	
	public static DoubleIlm create(DoubleIla doubleIla, long repetitions) {
		Argument.assertNotNull(doubleIla, "doubleIla");
		Argument.assertNotLessThan(repetitions, 1, "repetitions");
		
		return new MyDoubleIlm(doubleIla, repetitions);
	}
	
	private static class MyDoubleIlm extends AbstractDoubleIlm {
		private final DoubleIla doubleIla;
		
		private double[] buffer = new double[0];
		
		public MyDoubleIlm(DoubleIla doubleIla, long repetitions) {
			super(doubleIla.length(), repetitions);
			
			this.doubleIla = doubleIla;
		}

		@Override
		protected void toArrayImpl(double[] array, int offset, int rowStride, int colStride, long rowStart,
				long colStart, int rowCount, int colCount) throws DataInvalidException {
			if (colStride == 1) {
				doubleIla.toArray(array, offset, colStart, colCount);
				
				for (int i=0 ; i < rowCount ; i++) {
					System.arraycopy(array, offset, array, offset + (rowStride * i), colCount);
				}
			}
			else {
				if (buffer.length < colCount) {
					buffer = new double[colCount];
				}
				
				doubleIla.toArray(buffer, 0, colStart, colCount);
				
				for (int i=0 ; i < rowCount ; i++) {
					for (int j=0 ; j < colCount ; j++) {
						array[offset+(i*rowStride)+(j*colStride)] = buffer[j];
					}
				}
			}
		}
	}
}
