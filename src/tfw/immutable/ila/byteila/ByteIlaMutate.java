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
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ByteIlaMutate
{
    private ByteIlaMutate()
    {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, long index, byte value)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyByteIla(ila, index, value);
    }

    private static class MyByteIla extends AbstractByteIla
        implements ImmutableProxy
    {
        private final ByteIla ila;
        private final long index;
        private final byte value;

        MyByteIla(ByteIla ila, long index, byte value)
        {
            super(ila.length());
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        protected void toArrayImpl(byte[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            final long startPlusLength = start + length;

            if(index < start || index >= startPlusLength)
            {
                ila.toArray(array, offset, stride, start, length);
            }
            else
            {
                final int indexMinusStart = (int) (index - start);
                if(index > start)
                {
                    ila.toArray(array, offset, stride, start,
                                indexMinusStart);
                }
                array[offset + indexMinusStart * stride] = value;
                if(index <= startPlusLength)
                {
                    ila.toArray(array, offset + (indexMinusStart + 1) * stride,
                                stride, index + 1, length - 
                                indexMinusStart - 1);
                }
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ByteIlaMutate");
            map.put("length", new Long(length()));
            map.put("ila", getImmutableInfo(ila));
            map.put("index", new Long(index));
            map.put("value", new Byte(value));

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
