package tfw.immutable.ilm.booleanilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class BooleanIlmFromArray
{
    private BooleanIlmFromArray() {}

    public static BooleanIlm create(boolean[][] array)
    {
    	Argument.assertNotNull(array, "array");

		return new MyBooleanIlm(array);
    }

    private static class MyBooleanIlm extends AbstractBooleanIlm
    	implements ImmutableProxy
    {
		private final boolean[][] array;

		MyBooleanIlm(boolean[][] array)
		{
		    super(array.length > 0 ? array[0].length : 0, array.length);
		    
		    this.array = (boolean[][])array.clone();
		    
		    for (int i=0 ; i < array.length ; i++)
		    {
		    	Argument.assertNotNull(array[i], "array["+i+"]");
		    	Argument.assertEquals(array[i].length, width,
		    		"array["+i+"].length", "width");
		    	
		    	this.array[i] = (boolean[])array[i].clone();
		    }
		}

		protected void toArrayImpl(boolean[][] array, int rowOffset,
			int columnOffset, long rowStart, long columnStart,
			int width, int height)
		{
			for (int i=0 ; i < height ; i++)
			{
				System.arraycopy(this.array[(int)(rowStart + i)],
					(int)columnStart, array[rowOffset+i],
					columnOffset, width);
			} 
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "BooleanIlmFromArray");
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}
