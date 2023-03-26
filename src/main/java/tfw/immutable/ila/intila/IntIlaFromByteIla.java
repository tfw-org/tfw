package tfw.immutable.ila.intila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class IntIlaFromByteIla
{
    private IntIlaFromByteIla() {}

    public static IntIla create(ByteIla byteIla)
    {
    	Argument.assertNotNull(byteIla, "byteIla");
    	
		return new MyIntIla(byteIla);
    }

    private static class MyIntIla extends AbstractIntIla
		implements ImmutableProxy
    {
		private ByteIla byteIla;

		MyIntIla(ByteIla byteIla)
		{
		    super(byteIla.length() / 4);
		    
		    this.byteIla = byteIla;
		}

		protected void toArrayImpl(int[] array, int offset, int stride,
			long start, int length) throws DataInvalidException
		{
			ByteIlaIterator bii = new ByteIlaIterator(
				ByteIlaSegment.create(byteIla, 4 * start, 4 * length));
				
		    for (int i=0 ; i < length ; i++)
		    {
		    	array[offset+(i * stride)] =
					((bii.next() & 0xFF) << 24) |
					((bii.next() & 0xFF) << 16) |
					((bii.next() & 0xFF) <<  8) |
					((bii.next() & 0xFF));
		    }
		}
		
		public Map<String, Object> getParameters()
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("name", "IntIlaFromByteIla");
			map.put("byteIla", getImmutableInfo(byteIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}
