package tfw.immutable.ilm.charilm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.charila.AbstractCharIla;
import tfw.immutable.ila.charila.CharIla;

public final class CharIlmFromIncrementCharIla
{
    private CharIlmFromIncrementCharIla() {}

    public static CharIlm create(CharIla ila, BigDecimal rowIncrement,
    	char noDataValue)
    {
    	Argument.assertNotNull(ila, "ila");
    	Argument.assertNotNull(rowIncrement, "rowIncrement");
				
		return new MyCharIlm(ila, rowIncrement, noDataValue);
    }

    private static class MyCharIlm extends AbstractCharIlm
    	implements ImmutableProxy
    {
		private final CharIla ila;
		private final BigDecimal rowIncrement;
		private final char noDataValue;

		MyCharIlm(CharIla ila, BigDecimal rowIncrement,
			char noDataValue)
		{
		    super(ila.length(), new BigDecimal(ila.length()).divide(
		    	rowIncrement, BigDecimal.ROUND_UP).longValue());
		    
		    this.ila = ila;
		    this.rowIncrement = rowIncrement;
		    this.noDataValue = noDataValue;
		}
		
		protected void toArrayImpl(char[][] array, int rowOffset,
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
			
			map.put("name", "CharIlmFromIncrementCharIla");
			map.put("ila", AbstractCharIla.getImmutableInfo(ila));
			map.put("rowIncrement", rowIncrement);
			map.put("noDataValue", new Character(noDataValue));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}