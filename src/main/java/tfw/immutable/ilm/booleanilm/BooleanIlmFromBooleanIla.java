package tfw.immutable.ilm.booleanilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.booleanila.AbstractBooleanIla;
import tfw.immutable.ila.booleanila.BooleanIla;

public final class BooleanIlmFromBooleanIla
{
    private BooleanIlmFromBooleanIla() {}

    public static BooleanIlm create(BooleanIla ila, long width)
    {
    	Argument.assertNotNull(ila, "ila");
		if (ila.length() % width != 0)
			throw new IllegalArgumentException(
				"length not divisible by width!");
				
		return new MyBooleanIlm(ila, width);
    }

    private static class MyBooleanIlm extends AbstractBooleanIlm
    	implements ImmutableProxy
    {
		private BooleanIla ila;

		MyBooleanIlm(BooleanIla ila, long width)
		{
		    super(width, ila.length() / width);
		    
		    this.ila = ila;
		}
		
		protected void toArrayImpl(boolean[][] array, int rowOffset,
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
			
			map.put("name", "BooleanIlmFromBooleanIla");
			map.put("ila", AbstractBooleanIla.getImmutableInfo(ila));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}