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
package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class FloatIlaDecimate
{
    private FloatIlaDecimate()
    {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, long factor)
    {
        return create(ila, factor, FloatIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static FloatIla create(FloatIla ila, long factor, int bufferSize)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyFloatIla(ila, factor, bufferSize);
    }

    private static class MyFloatIla extends AbstractFloatIla
        implements ImmutableProxy
    {
        private final FloatIla ila;
        private final long factor;
        private final int bufferSize;

        MyFloatIla(FloatIla ila, long factor, int bufferSize)
        {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(float[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() -
                segmentStart, length * factor - 1);
            FloatIlaIterator fi = new FloatIlaIterator(
                FloatIlaSegment.create(ila, segmentStart, segmentLength),
                    bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (float) fi.next();
                fi.skip(factor - 1);
            }
        }
                
        public Map getParameters()
        {
            HashMap map = new HashMap();
                        
            map.put("name", "FloatIlaDecimate");
            map.put("ila", getImmutableInfo(ila));
            map.put("length", new Long(length()));
            map.put("factor", new Long(factor));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
