package tfw.immutable.ilm.charilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class CharIlmFromArray
{
    private CharIlmFromArray() {}

    public static CharIlm create(char[][] array)
    {
    	Argument.assertNotNull(array, "array");

		return new MyCharIlm(array);
    }

    private static class MyCharIlm extends AbstractCharIlm
    	implements ImmutableProxy
    {
		private final char[][] array;

		MyCharIlm(char[][] array)
		{
		    super(array.length > 0 ? array[0].length : 0, array.length);
		    
		    this.array = (char[][])array.clone();
		    
		    for (int i=0 ; i < array.length ; i++)
		    {
		    	Argument.assertNotNull(array[i], "array["+i+"]");
		    	Argument.assertEquals(array[i].length, width,
		    		"array["+i+"].length", "width");
		    	
		    	this.array[i] = (char[])array[i].clone();
		    }
		}

		protected void toArrayImpl(char[][] array, int rowOffset,
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
			
			map.put("name", "CharIlmFromArray");
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}
