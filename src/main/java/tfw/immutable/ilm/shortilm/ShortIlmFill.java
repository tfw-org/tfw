package tfw.immutable.ilm.shortilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class ShortIlmFill
{
    private ShortIlmFill() {}

    public static ShortIlm create(short value, long width, long height)
    {
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");

		return new MyShortIlm(width, height, value);
    }

    private static class MyShortIlm extends AbstractShortIlm
    	implements ImmutableProxy
    {
		private final short value;

		MyShortIlm(long width, long height, short value)
		{
		    super(width, height);
		    
		    this.value = value;
		}

		protected void toArrayImpl(short[][] array, int rowOffset,
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
			
			map.put("name", "ShortIlmFill");
			map.put("value", new Short(value));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}