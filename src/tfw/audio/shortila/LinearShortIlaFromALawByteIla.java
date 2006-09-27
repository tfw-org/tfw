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
 * witout even the implied warranty of
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
		    	
		    	array[offset + i] = (short)(((a_val & SIGN_BIT) != 0) ? s : -s);
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