package tfw.immutable.ila.shortila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class ShortIlaFromByteIla
{
    private ShortIlaFromByteIla() {}

    public static ShortIla create(ByteIla byteIla)
    {
    	Argument.assertNotNull(byteIla, "byteIla");

		return new MyShortIla(byteIla);
    }

    private static class MyShortIla extends AbstractShortIla
		implements ImmutableProxy
    {
		private ByteIla byteIla;

		MyShortIla(ByteIla byteIla)
		{
		    super(byteIla.length() / 2);
		    
		    this.byteIla = byteIla;
		}

		protected void toArrayImpl(short[] array, int offset, int stride,
			long start, int length) throws DataInvalidException
		{
			ByteIlaIterator bii = new ByteIlaIterator(
				ByteIlaSegment.create(byteIla, 2 * start, 2 * length));
				
		    for (int i=0 ; i < length ; i++)
		    {
		    	array[offset+(i * stride)] = (short)
					(((bii.next() & 0xFF) << 8) |
					((bii.next() & 0xFF)));
		    }
		}
		
		public Map<String, Object> getParameters()
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("name", "ShortIlaFromByteIla");
			map.put("byteIla", getImmutableInfo(byteIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}
