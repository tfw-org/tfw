/*
 * The Framework Project
 * Copyright (C) 2006 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
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

		protected void toArrayImpl(short[] array, int offset,
			long start, int length) throws DataInvalidException
		{
		    ByteIlaIterator bi = new ByteIlaIterator(
		    	ByteIlaSegment.create(byteIla, start, length));
		    
		    for (int i=0 ; bi.hasNext() ; i++)
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
		    		array[offset+i] = (short)(t - BIAS);
		    	}
		    	else
		    	{
		    		array[offset+i] = (short)(BIAS - t);
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