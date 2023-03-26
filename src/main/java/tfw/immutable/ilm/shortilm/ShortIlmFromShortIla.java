package tfw.immutable.ilm.shortilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.shortila.AbstractShortIla;
import tfw.immutable.ila.shortila.ShortIla;

public final class ShortIlmFromShortIla
{
    private ShortIlmFromShortIla() {}

    public static ShortIlm create(ShortIla ila, long width)
    {
    	Argument.assertNotNull(ila, "ila");
		if (ila.length() % width != 0)
			throw new IllegalArgumentException(
				"length not divisible by width!");
				
		return new MyShortIlm(ila, width);
    }

    private static class MyShortIlm extends AbstractShortIlm
    	implements ImmutableProxy
    {
		private ShortIla ila;

		MyShortIlm(ShortIla ila, long width)
		{
		    super(width, ila.length() / width);
		    
		    this.ila = ila;
		}
		
		protected void toArrayImpl(short[][] array, int rowOffset,
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
			
			map.put("name", "ShortIlmFromShortIla");
			map.put("ila", AbstractShortIla.getImmutableInfo(ila));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}