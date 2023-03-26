package tfw.immutable.ilm.doubleilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.doubleila.AbstractDoubleIla;
import tfw.immutable.ila.doubleila.DoubleIla;

public final class DoubleIlmFromDoubleIla
{
    private DoubleIlmFromDoubleIla() {}

    public static DoubleIlm create(DoubleIla ila, long width)
    {
    	Argument.assertNotNull(ila, "ila");
		if (ila.length() % width != 0)
			throw new IllegalArgumentException(
				"length not divisible by width!");
				
		return new MyDoubleIlm(ila, width);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm
    	implements ImmutableProxy
    {
		private DoubleIla ila;

		MyDoubleIlm(DoubleIla ila, long width)
		{
		    super(width, ila.length() / width);
		    
		    this.ila = ila;
		}
		
		protected void toArrayImpl(double[][] array, int rowOffset,
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
			
			map.put("name", "DoubleIlmFromDoubleIla");
			map.put("ila", AbstractDoubleIla.getImmutableInfo(ila));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}