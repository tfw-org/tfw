package tfw.immutable.ila.longila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class LongIlaFromByteIla
{
    private LongIlaFromByteIla() {}

    public static LongIla create(ByteIla byteIla)
    {
    	Argument.assertNotNull(byteIla, "byteIla");

		return new MyLongIla(byteIla);
    }

    private static class MyLongIla extends AbstractLongIla
		implements ImmutableProxy
    {
		private ByteIla byteIla;

		MyLongIla(ByteIla byteIla)
		{
		    super(byteIla.length() / 8);
		    
		    this.byteIla = byteIla;
		}

		protected void toArrayImpl(long[] array, int offset, int stride,
			long start, int length) throws DataInvalidException
		{
			ByteIlaIterator bii = new ByteIlaIterator(
				ByteIlaSegment.create(byteIla, 8 * start, 8 * length));
				
		    for (int i=0 ; i < length ; i++)
		    {
		    	array[offset+(i * stride)] =
		    		(((long)bii.next() & 0xFF) << 56) |
					(((long)bii.next() & 0xFF) << 48) |
					(((long)bii.next() & 0xFF) << 40) |
					(((long)bii.next() & 0xFF) << 32) |
					(((long)bii.next() & 0xFF) << 24) |
					(((long)bii.next() & 0xFF) << 16) |
					(((long)bii.next() & 0xFF) <<  8) |
					(((long)bii.next() & 0xFF));
		    }
		}
		
		public Map<String, Object> getParameters()
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("name", "LongIlaFromByteIla");
			map.put("byteIla", getImmutableInfo(byteIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}
