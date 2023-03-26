package tfw.immutable.ilm.floatilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.floatila.AbstractFloatIla;
import tfw.immutable.ila.floatila.FloatIla;

public final class FloatIlmFromFloatIla
{
    private FloatIlmFromFloatIla() {}

    public static FloatIlm create(FloatIla ila, long width)
    {
    	Argument.assertNotNull(ila, "ila");
		if (ila.length() % width != 0)
			throw new IllegalArgumentException(
				"length not divisible by width!");
				
		return new MyFloatIlm(ila, width);
    }

    private static class MyFloatIlm extends AbstractFloatIlm
    	implements ImmutableProxy
    {
		private FloatIla ila;

		MyFloatIlm(FloatIla ila, long width)
		{
		    super(width, ila.length() / width);
		    
		    this.ila = ila;
		}
		
		protected void toArrayImpl(float[][] array, int rowOffset,
			int columnOffset, long rowStart, long columnStart,
			int width, int height) throws DataInvalidException
		{
			for (int r=0 ; r < height ; r++)
			{
				ila.toArray(array[rowOffset+r], columnOffset,
					(rowStart + r) * this.width + columnStart, width);
			}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "FloatIlmFromFloatIla");
			map.put("ila", AbstractFloatIla.getImmutableInfo(ila));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}