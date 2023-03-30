package tfw.immutable.ilm.doubleilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class DoubleIlmFromArray
{
    private DoubleIlmFromArray() {}

    public static DoubleIlm create(double[][] array)
    {
    	Argument.assertNotNull(array, "array");

		return new MyDoubleIlm(array);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm
    	implements ImmutableProxy
    {
		private final double[][] array;

		MyDoubleIlm(double[][] array)
		{
		    super(array.length > 0 ? array[0].length : 0, array.length);
		    
		    this.array = (double[][])array.clone();
		    
		    for (int i=0 ; i < array.length ; i++)
		    {
		    	Argument.assertNotNull(array[i], "array["+i+"]");
		    	Argument.assertEquals(array[i].length, width,
		    		"array["+i+"].length", "width");
		    	
		    	this.array[i] = (double[])array[i].clone();
		    }
		}

		protected void toArrayImpl(double[][] array, int rowOffset,
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
			
			map.put("name", "DoubleIlmFromArray");
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}
