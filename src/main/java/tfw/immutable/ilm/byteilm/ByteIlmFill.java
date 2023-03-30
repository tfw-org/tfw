package tfw.immutable.ilm.byteilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class ByteIlmFill
{
    private ByteIlmFill() {}

    public static ByteIlm create(byte value, long width, long height)
    {
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");

		return new MyByteIlm(width, height, value);
    }

    private static class MyByteIlm extends AbstractByteIlm
    	implements ImmutableProxy
    {
		private final byte value;

		MyByteIlm(long width, long height, byte value)
		{
		    super(width, height);
		    
		    this.value = value;
		}

		protected void toArrayImpl(byte[][] array, int rowOffset,
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
			
			map.put("name", "ByteIlmFill");
			map.put("value", new Byte(value));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}