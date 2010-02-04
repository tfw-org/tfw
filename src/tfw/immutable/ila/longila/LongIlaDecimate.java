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
package tfw.immutable.ila.longila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class LongIlaDecimate
{
    private LongIlaDecimate()
    {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, long factor)
    {
        return create(ila, factor, LongIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static LongIla create(LongIla ila, long factor, int bufferSize)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyLongIla(ila, factor, bufferSize);
    }

    private static class MyLongIla extends AbstractLongIla
        implements ImmutableProxy
    {
        private final LongIla ila;
        private final long factor;
        private final int bufferSize;

        MyLongIla(LongIla ila, long factor, int bufferSize)
        {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(long[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() -
                segmentStart, length * factor - 1);
            LongIlaIterator fi = new LongIlaIterator(
                LongIlaSegment.create(ila, segmentStart, segmentLength),
                    bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (long) fi.next();
                fi.skip(factor - 1);
            }
        }
                
        public Map getParameters()
        {
            HashMap map = new HashMap();
                        
            map.put("name", "LongIlaDecimate");
            map.put("ila", getImmutableInfo(ila));
            map.put("length", new Long(length()));
            map.put("factor", new Long(factor));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
