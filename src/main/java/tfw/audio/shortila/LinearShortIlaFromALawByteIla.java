package tfw.audio.shortila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;
import tfw.immutable.ila.shortila.AbstractShortIla;
import tfw.immutable.ila.shortila.ShortIla;

public final class LinearShortIlaFromALawByteIla
{
    private static final int QUANT_MASK = 0xf;
    private static final int SEG_MASK = 0x70;
    private static final int SEG_SHIFT = 4;
    private static final int SIGN_BIT = 0x80;

    private LinearShortIlaFromALawByteIla() {}

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
		    super(byteIla.length());
		    
		    this.byteIla = byteIla;
		}

		protected void toArrayImpl(short[] array, int offset, int stride,
			long start, int length) throws DataInvalidException
		{
		    ByteIlaIterator bi = new ByteIlaIterator(
		    	ByteIlaSegment.create(byteIla, start, length));
		    
		    for (int i=offset ; bi.hasNext() ; i+=stride)
		    {
		    	/*
		    	 * The following algorithm is from the file g711.c from
		    	 * Sun Microsystems which has no use restrictions.  It is
		    	 * available from the following location:
		    	 * ftp://svr-ftp.eng.cam.ac.uk/pub/comp.speech/coding/G711_G721_G723.tar.gz
		    	 */
		    	int a_val = bi.next();
		    	
		    	a_val ^= 0x55;

		    	int t = (a_val & QUANT_MASK) << 4;
		    	int seg = (a_val & SEG_MASK) >> SEG_SHIFT;
		    	
		    	switch (seg)
		    	{
		    		case 0:
		    			t += 8;
		    			break;
		    		case 1:
		    			t += 0x108;
		    			break;
		    		default:
		    			t += 0x108;
		    			t <<= seg - 1;
		    	}
		    	
		    	short s = (short)t;
		    	
		    	array[i] = (short)(((a_val & SIGN_BIT) != 0) ? s : -s);
		    }
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "LinearShortIlaFromALawByteIla");
			map.put("byteIla", getImmutableInfo(byteIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}