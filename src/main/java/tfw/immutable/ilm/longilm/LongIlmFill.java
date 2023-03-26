package tfw.immutable.ilm.longilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class LongIlmFill
{
    private LongIlmFill() {}

    public static LongIlm create(long value, long width, long height)
    {
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");

		return new MyLongIlm(width, height, value);
    }

    private static class MyLongIlm extends AbstractLongIlm
    	implements ImmutableProxy
    {
		private final long value;

		MyLongIlm(long width, long height, long value)
		{
		    super(width, height);
		    
		    this.value = value;
		}

		protected void toArrayImpl(long[][] array, int rowOffset,
			int columnOffset, long rowStart, long columnStart,
			int width, int height)
		{
			for (int r=0 ; r < height ; r++)
			{
				for (int c=0 ; c < width ; c++)
				{
					array[rowOffset+r][columnOffset+c] = value;
				}
			} 
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "LongIlmFill");
			map.put("value", new Long(value));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}