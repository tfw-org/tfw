/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
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
package tfw.immutable.ila.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

/**
 *
 * @immutables.types=numericnotshort
 */
public final class ByteIlaFromCastShortIla
{
    private ByteIlaFromCastShortIla()
    {
    	// non-instantiable class
    }

    public static ByteIla create(ShortIla shortIla)
    {
        return create(shortIla, ShortIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ByteIla create(ShortIla shortIla, int bufferSize)
    {
        Argument.assertNotNull(shortIla, "shortIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(shortIla, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla
        implements ImmutableProxy
    {
        private final ShortIla shortIla;
        private final int bufferSize;

        MyByteIla(ShortIla shortIla, int bufferSize)
        {
            super(shortIla.length());
                    
            this.shortIla = shortIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(byte[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ShortIlaIterator fi = new ShortIlaIterator(
                ShortIlaSegment.create(shortIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (byte) fi.next();
            }
        }
                
        public Map getParameters()
        {
            HashMap map = new HashMap();
                        
            map.put("name", "ByteIlaFromCastShortIla");
            map.put("shortIla", getImmutableInfo(shortIla));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
