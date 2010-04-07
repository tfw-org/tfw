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
package tfw.immutable.ila.shortila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

/**
 *
 * @immutables.types=numericnotfloat
 */
public final class ShortIlaFromCastFloatIla
{
    private ShortIlaFromCastFloatIla()
    {
    	// non-instantiable class
    }

    public static ShortIla create(FloatIla floatIla)
    {
        return create(floatIla, FloatIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ShortIla create(FloatIla floatIla, int bufferSize)
    {
        Argument.assertNotNull(floatIla, "floatIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyShortIla(floatIla, bufferSize);
    }

    private static class MyShortIla extends AbstractShortIla
        implements ImmutableProxy
    {
        private final FloatIla floatIla;
        private final int bufferSize;

        MyShortIla(FloatIla floatIla, int bufferSize)
        {
            super(floatIla.length());
                    
            this.floatIla = floatIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(short[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            FloatIlaIterator fi = new FloatIlaIterator(
                FloatIlaSegment.create(floatIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (short) fi.next();
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ShortIlaFromCastFloatIla");
            map.put("floatIla", getImmutableInfo(floatIla));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
