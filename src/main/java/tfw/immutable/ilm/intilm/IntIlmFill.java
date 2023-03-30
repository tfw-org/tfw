package tfw.immutable.ilm.intilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class IntIlmFill
{
    private IntIlmFill() {}

    public static IntIlm create(int value, long width, long height)
    {
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");

		return new MyIntIlm(width, height, value);
    }

    private static class MyIntIlm extends AbstractIntIlm
    	implements ImmutableProxy
    {
		private final int value;

		MyIntIlm(long width, long height, int value)
		{
		    super(width, height);
		    
		    this.value = value;
		}

		protected void toArrayImpl(int[][] array, int rowOffset,
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
			
			map.put("name", "IntIlmFill");
			map.put("value", new Integer(value));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}