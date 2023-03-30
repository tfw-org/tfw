package tfw.immutable.ilm.booleanilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class BooleanIlmFill
{
    private BooleanIlmFill() {}

    public static BooleanIlm create(boolean value, long width, long height)
    {
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");

		return new MyBooleanIlm(width, height, value);
    }

    private static class MyBooleanIlm extends AbstractBooleanIlm
    	implements ImmutableProxy
    {
		private final boolean value;

		MyBooleanIlm(long width, long height, boolean value)
		{
		    super(width, height);
		    
		    this.value = value;
		}

		protected void toArrayImpl(boolean[][] array, int rowOffset,
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
			
			map.put("name", "BooleanIlmFill");
			map.put("value", new Boolean(value));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}