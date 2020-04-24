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
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

/**
 *
 * @immutables.types=numericnotdouble
 */
public final class ByteIlaFromCastDoubleIla
{
    private ByteIlaFromCastDoubleIla()
    {
    	// non-instantiable class
    }

    public static ByteIla create(DoubleIla doubleIla)
    {
        return create(doubleIla, DoubleIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ByteIla create(DoubleIla doubleIla, int bufferSize)
    {
        Argument.assertNotNull(doubleIla, "doubleIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(doubleIla, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla
        implements ImmutableProxy
    {
        private final DoubleIla doubleIla;
        private final int bufferSize;

        MyByteIla(DoubleIla doubleIla, int bufferSize)
        {
            super(doubleIla.length());
                    
            this.doubleIla = doubleIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(byte[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            DoubleIlaIterator fi = new DoubleIlaIterator(
                DoubleIlaSegment.create(doubleIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (byte) fi.next();
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ByteIlaFromCastDoubleIla");
            map.put("doubleIla", getImmutableInfo(doubleIla));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
