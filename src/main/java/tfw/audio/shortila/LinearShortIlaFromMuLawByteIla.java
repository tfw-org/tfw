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

public final class LinearShortIlaFromMuLawByteIla
{
    private static final int BIAS = 0x84;
    private static final int QUANT_MASK = 0xf;
    private static final int SEG_MASK = 0x70;
    private static final int SEG_SHIFT = 4;
    private static final int SIGN_BIT = 0x80;

    private LinearShortIlaFromMuLawByteIla() {}

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
		    	int u_val = ~bi.next();
		    	
		    	int t = ((u_val & QUANT_MASK) << 3) + BIAS;
		    	
		    	t <<= (u_val & SEG_MASK) >> SEG_SHIFT;
		    	
		    	if ((u_val & SIGN_BIT) == 0)
		    	{
		    		array[i] = (short)(t - BIAS);
		    	}
		    	else
		    	{
		    		array[i] = (short)(BIAS - t);
		    	}
		    }
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "ShortIlaFromMuLawByteIla");
			map.put("byteIla", getImmutableInfo(byteIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}