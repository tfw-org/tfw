package tfw.audio.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.byteila.AbstractByteIla;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public final class ALawByteIlaFromLinearShortIla
{
    private static final int QUANT_MASK = 0xf;
    private static final int SEG_SHIFT = 4;

    private ALawByteIlaFromLinearShortIla() {}

    public static ByteIla create(ShortIla shortIla)
    {
    	Argument.assertNotNull(shortIla, "shortIla");

		return new MyByteIla(shortIla);
    }

    private static class MyByteIla extends AbstractByteIla
    	implements ImmutableProxy
    {
		private ShortIla shortIla;

		MyByteIla(ShortIla shortIla)
		{
		    super(shortIla.length());
		    
		    this.shortIla = shortIla;
		}

		protected void toArrayImpl(byte[] array, int offset, int stride,
			long start, int length) throws DataInvalidException
		{
		    ShortIlaIterator si = new ShortIlaIterator(
		    	ShortIlaSegment.create(shortIla, start, length));
		    
		    for (int i=offset ; si.hasNext() ; i+=stride)
		    {
		    	/*
		    	 * The following algorithm is from the file g711.c from
		    	 * Sun Microsystems which has no use restrictions.  It is
		    	 * available from the following location:
		    	 * ftp://svr-ftp.eng.cam.ac.uk/pub/comp.speech/coding/G711_G721_G723.tar.gz
		    	 */
		    	short pcm_val = si.next();
		    	int mask;

		    	/* Get the sign and the magnitude of the value. */
		    	if (pcm_val >= 0)
		    	{
		    		mask = 0xD5;		/* sign (7th) bit = 1 */
		    	}
		    	else
		    	{
		    		mask = 0x55;		/* sign bit = 0 */
		    		pcm_val = (short)(-pcm_val - 8);
		    	}
		    		
		    	/* Convert the scaled magnitude to segment number. */
		    	int seg = 8;
		    	
		    	if (pcm_val <= 0xFF)
		    		seg = 0;
		    	else if (pcm_val <= 0x1FF)
		    		seg = 1;
		    	else if (pcm_val <= 0x3FF)
		    		seg = 2;
		    	else if (pcm_val <= 0x7FF)
		    		seg = 3;
		    	else if (pcm_val <= 0xFFF)
		    		seg = 4;
		    	else if (pcm_val <= 0x1FFF)
		    		seg = 5;
		    	else if (pcm_val <= 0x3FFF)
		    		seg = 6;
		    	else if (pcm_val <= 0x7FFF)
		    		seg = 7;

		    	/* Combine the sign, segment, and quantization bits. */

		    	if (seg >= 8)		/* out of range, return maximum value. */
		    		array[i] = (byte)(0x7F ^ mask);
		    	else {
		    		int aval = seg << SEG_SHIFT;
		    		if (seg < 2)
		    			aval |= (pcm_val >> 4) & QUANT_MASK;
		    		else
		    			aval |= (pcm_val >> (seg + 3)) & QUANT_MASK;
		    		array[i] = (byte)(aval ^ mask);
		    	}
		    }
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "ALawByteIlaFromLinearShortIla");
			map.put("shortIla", getImmutableInfo(shortIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}