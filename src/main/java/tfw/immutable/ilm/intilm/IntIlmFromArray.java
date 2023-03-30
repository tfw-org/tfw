package tfw.immutable.ilm.intilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class IntIlmFromArray
{
    private IntIlmFromArray() {}

    public static IntIlm create(int[][] array)
    {
    	Argument.assertNotNull(array, "array");

		return new MyIntIlm(array);
    }

    private static class MyIntIlm extends AbstractIntIlm
    	implements ImmutableProxy
    {
		private final int[][] array;

		MyIntIlm(int[][] array)
		{
		    super(array.length > 0 ? array[0].length : 0, array.length);
		    
		    this.array = (int[][])array.clone();
		    
		    for (int i=0 ; i < array.length ; i++)
		    {
		    	Argument.assertNotNull(array[i], "array["+i+"]");
		    	Argument.assertEquals(array[i].length, width,
		    		"array["+i+"].length", "width");
		    	
		    	this.array[i] = (int[])array[i].clone();
		    }
		}

		protected void toArrayImpl(int[][] array, int rowOffset,
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
			
			map.put("name", "IntIlmFromArray");
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}
