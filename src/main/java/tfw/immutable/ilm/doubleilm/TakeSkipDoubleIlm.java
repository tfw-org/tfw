package tfw.immutable.ilm.doubleilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.doubleila.DoubleIla;

public class TakeSkipDoubleIlm {
	private TakeSkipDoubleIlm() {}
	
	public static DoubleIlm create(DoubleIla doubleIla, long take, long skip) {
		Argument.assertNotNull(doubleIla, "doubleIla");
		Argument.assertNotLessThan(take, 1, "take");
		Argument.assertNotLessThan(skip, 1, "skip");
		
		return new MyDoubleIlm(doubleIla, take, skip);
	}
	
	private static class MyDoubleIlm extends AbstractDoubleIlm {
		private final DoubleIla doubleIla;
		private final long skip;
		
		private double[] buffer = new double[0];
		
		public MyDoubleIlm(DoubleIla doubleIla, long take, long skip) {
			super(take, (doubleIla.length() - take) / skip + 1);
			
			this.doubleIla = doubleIla;
			this.skip = skip;
		}

		@Override
		protected void toArrayImpl(double[] array, int offset, int rowStride, int colStride, long rowStart,
				long colStart, int rowCount, int colCount) throws DataInvalidException {
			if (colStride == 1) {
				for (int i=0 ; i < rowCount ; i++) {
					doubleIla.toArray(array, offset + i * rowStride, (rowStart + i) * skip + colStart, colCount);
				}
			}
			else {
				if (buffer.length < colCount) {
					buffer = new double[colCount];
				}
				
				for (int i=0 ; i < rowCount ; i++) {
					doubleIla.toArray(buffer, 0, (rowStart + i) * skip + colStart, colCount);
					
					for (int j=0 ; j < colCount ; j++) {
						array[offset+(i*rowStride)+(j*colStride)] = buffer[j];
					}
				}
			}
		}
	}
}
