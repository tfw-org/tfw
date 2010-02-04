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
package tfw.immutable.ila.intila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

/**
 *
 * @immutables.types=numericnotbyte
 */
public final class IntIlaFromCastByteIla
{
    private IntIlaFromCastByteIla()
    {
    	// non-instantiable class
    }

    public static IntIla create(ByteIla byteIla)
    {
        return create(byteIla, ByteIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static IntIla create(ByteIla byteIla, int bufferSize)
    {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyIntIla(byteIla, bufferSize);
    }

    private static class MyIntIla extends AbstractIntIla
        implements ImmutableProxy
    {
        private final ByteIla byteIla;
        private final int bufferSize;

        MyIntIla(ByteIla byteIla, int bufferSize)
        {
            super(byteIla.length());
                    
            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(int[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ByteIlaIterator fi = new ByteIlaIterator(
                ByteIlaSegment.create(byteIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (int) fi.next();
            }
        }
                
        public Map getParameters()
        {
            HashMap map = new HashMap();
                        
            map.put("name", "IntIlaFromCastByteIla");
            map.put("byteIla", getImmutableInfo(byteIla));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
