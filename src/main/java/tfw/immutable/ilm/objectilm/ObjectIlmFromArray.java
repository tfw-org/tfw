package tfw.immutable.ilm.objectilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class ObjectIlmFromArray
{
    private ObjectIlmFromArray() {}

    public static ObjectIlm create(Object[][] array)
    {
    	Argument.assertNotNull(array, "array");

		return new MyObjectIlm(array);
    }

    private static class MyObjectIlm extends AbstractObjectIlm
    	implements ImmutableProxy
    {
		private final Object[][] array;

		MyObjectIlm(Object[][] array)
		{
		    super(array.length > 0 ? array[0].length : 0, array.length);
		    
		    this.array = (Object[][])array.clone();
		    
		    for (int i=0 ; i < array.length ; i++)
		    {
		    	Argument.assertNotNull(array[i], "array["+i+"]");
		    	Argument.assertEquals(array[i].length, width,
		    		"array["+i+"].length", "width");
		    	
		    	this.array[i] = (Object[])array[i].clone();
		    }
		}

		protected void toArrayImpl(Object[][] array, int rowOffset,
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
			
			map.put("name", "ObjectIlmFromArray");
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}
