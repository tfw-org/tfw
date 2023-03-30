package tfw.immutable.ilm.intilm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.intila.AbstractIntIla;
import tfw.immutable.ila.intila.IntIla;

public final class IntIlmFromIncrementIntIla
{
    private IntIlmFromIncrementIntIla() {}

    public static IntIlm create(IntIla ila, BigDecimal rowIncrement,
    	int noDataValue)
    {
    	Argument.assertNotNull(ila, "ila");
    	Argument.assertNotNull(rowIncrement, "rowIncrement");
				
		return new MyIntIlm(ila, rowIncrement, noDataValue);
    }

    private static class MyIntIlm extends AbstractIntIlm
    	implements ImmutableProxy
    {
		private final IntIla ila;
		private final BigDecimal rowIncrement;
		private final int noDataValue;

		MyIntIlm(IntIla ila, BigDecimal rowIncrement,
			int noDataValue)
		{
		    super(ila.length(), new BigDecimal(ila.length()).divide(
		    	rowIncrement, BigDecimal.ROUND_UP).longValue());
		    
		    this.ila = ila;
		    this.rowIncrement = rowIncrement;
		    this.noDataValue = noDataValue;
		}
		
		protected void toArrayImpl(int[][] array, int rowOffset,
			int columnOffset, long rowStart, long columnStart,
			int width, int height) throws DataInvalidException
		{
			for (int r=0 ; r < height ; r++)
			{
				long startIlaFromRowIndex = new BigDecimal(r+rowStart)
					.multiply(rowIncrement).longValue();
				long ilaEndIndex = ila.length() - startIlaFromRowIndex - 1;
				long ilaStart = startIlaFromRowIndex + columnStart;

			    if (columnStart + width - 1 <= ilaEndIndex)
			    {
					ila.toArray(array[r+rowOffset], columnOffset,
						ilaStart, width);
			    }
			    else if (columnStart > ilaEndIndex)
				{
					Arrays.fill(array[r+rowOffset], columnOffset,
						columnOffset + width, noDataValue);
			    }
			    else
				{
					int ilaSubsetLength = (int)(ilaEndIndex - columnStart + 1);

					ila.toArray(array[r+rowOffset], columnOffset,
						ilaStart, ilaSubsetLength);
					Arrays.fill(array[r+rowOffset],
						columnOffset + ilaSubsetLength,
						columnOffset + width, noDataValue);
				}
	    	}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "IntIlmFromIncrementIntIla");
			map.put("ila", AbstractIntIla.getImmutableInfo(ila));
			map.put("rowIncrement", rowIncrement);
			map.put("noDataValue", new Integer(noDataValue));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}