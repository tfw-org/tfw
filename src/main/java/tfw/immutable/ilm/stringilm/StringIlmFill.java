package tfw.immutable.ilm.stringilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class StringIlmFill
{
    private StringIlmFill() {}

    public static StringIlm create(String value, long width, long height)
    {
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");

		return new MyStringIlm(width, height, value);
    }

    private static class MyStringIlm extends AbstractStringIlm
    	implements ImmutableProxy
    {
		private final String value;

		MyStringIlm(long width, long height, String value)
		{
		    super(width, height);
		    
		    this.value = value;
		}

		protected void toArrayImpl(String[][] array, int rowOffset,
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
			
			map.put("name", "StringIlmFill");
			map.put("value", value);
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}