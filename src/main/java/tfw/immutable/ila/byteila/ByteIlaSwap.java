package tfw.immutable.ila.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public class ByteIlaSwap
{
    private ByteIlaSwap() {}

    public static ByteIla create(ByteIla byteIla, int bytesToSwap)
    {
		if(byteIla == null)
	    	throw new IllegalArgumentException
				("byteIla == null not allowed");

		return new MyByteIla(byteIla, bytesToSwap);
    }

    private static class MyByteIla extends AbstractByteIla
		implements ImmutableProxy
    {
		private final ByteIla byteIla;
		private final int bytesToSwap;

		MyByteIla(ByteIla byteIla, int bytesToSwap)
		{
		    super(byteIla.length() - (byteIla.length() % bytesToSwap));
		    
		    this.byteIla = byteIla;
		    this.bytesToSwap = bytesToSwap;
		}

		protected void toArrayImpl(byte[] array, int offset, int stride,
			long start, int length) throws DataInvalidException
		{
			long end = start + length - 1;
			long blockStart = start - (start % bytesToSwap);
			long blockEnd = end + bytesToSwap - (end % bytesToSwap) - 1;
			int blockLength = (int)(blockEnd - blockStart + 1);
		    ByteIlaIterator bii = new ByteIlaIterator(
		    	ByteIlaSegment.create(byteIla, blockStart, blockLength));
		    byte[] bytes = new byte[bytesToSwap];

			for (int j=bytesToSwap-1 ; j >= 0 ; j--)
			{
				bytes[j] = bii.next();
			}
		    int bs = (int)(start % bytesToSwap);
		    
		    for (int l=0 ; l < length ; l++,bs++)
		    {
		    	if (bs == bytesToSwap)
		    	{
		    		for (int j=bytesToSwap-1 ; j >= 0 ; j--)
		    		{
		    			bytes[j] = bii.next();
		    		}
		    		bs = 0;
		    	}

		    	array[offset+(l * stride)] = bytes[bs];
		    }
		}
		
		public Map<String, Object> getParameters()
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("name", "ByteIlaSwap");
			map.put("byteIla", getImmutableInfo(byteIla));
			map.put("bytesToSwap", new Integer(bytesToSwap));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}