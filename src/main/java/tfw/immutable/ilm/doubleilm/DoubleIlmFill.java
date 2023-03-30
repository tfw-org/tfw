package tfw.immutable.ilm.doubleilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class DoubleIlmFill
{
    private DoubleIlmFill() {}

    public static DoubleIlm create(double value, long width, long height)
    {
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");

		return new MyDoubleIlm(width, height, value);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm
    	implements ImmutableProxy
    {
		private final double value;

		MyDoubleIlm(long width, long height, double value)
		{
		    super(width, height);
		    
		    this.value = value;
		}

		protected void toArrayImpl(double[][] array, int rowOffset,
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
			
			map.put("name", "DoubleIlmFill");
			map.put("value", new Double(value));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}