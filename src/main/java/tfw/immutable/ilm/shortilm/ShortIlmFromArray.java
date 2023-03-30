package tfw.immutable.ilm.shortilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class ShortIlmFromArray
{
    private ShortIlmFromArray() {}

    public static ShortIlm create(short[][] array)
    {
    	Argument.assertNotNull(array, "array");

		return new MyShortIlm(array);
    }

    private static class MyShortIlm extends AbstractShortIlm
    	implements ImmutableProxy
    {
		private final short[][] array;

		MyShortIlm(short[][] array)
		{
		    super(array.length > 0 ? array[0].length : 0, array.length);
		    
		    this.array = (short[][])array.clone();
		    
		    for (int i=0 ; i < array.length ; i++)
		    {
		    	Argument.assertNotNull(array[i], "array["+i+"]");
		    	Argument.assertEquals(array[i].length, width,
		    		"array["+i+"].length", "width");
		    	
		    	this.array[i] = (short[])array[i].clone();
		    }
		}

		protected void toArrayImpl(short[][] array, int rowOffset,
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
			
			map.put("name", "ShortIlmFromArray");
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}
