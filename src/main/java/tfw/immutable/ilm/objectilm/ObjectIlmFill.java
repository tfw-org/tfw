package tfw.immutable.ilm.objectilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class ObjectIlmFill
{
    private ObjectIlmFill() {}

    public static ObjectIlm create(Object value, long width, long height)
    {
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");

		return new MyObjectIlm(width, height, value);
    }

    private static class MyObjectIlm extends AbstractObjectIlm
    	implements ImmutableProxy
    {
		private final Object value;

		MyObjectIlm(long width, long height, Object value)
		{
		    super(width, height);
		    
		    this.value = value;
		}

		protected void toArrayImpl(Object[][] array, int rowOffset,
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
			
			map.put("name", "ObjectIlmFill");
			map.put("value", value);
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}