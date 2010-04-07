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

/**
 *
 * @immutables.types=numeric
 */
public final class LongIlaRamp
{
    private LongIlaRamp()
    {
        // non-instantiable class
    }

    public static LongIla create(long startValue, long increment,
                                   long length)
    {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyLongIla(startValue, increment, length);
    }

    private static class MyLongIla extends AbstractLongIla
        implements ImmutableProxy
    {
        private final long startValue;
        private final long increment;

        MyLongIla(long startValue, long increment, long length)
        {
            super(length);
            this.startValue = startValue;
            this.increment = increment;
        }

        protected void toArrayImpl(long[] array, int offset,
                                   int stride, long start, int length)
        {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            //long value = startValue;
            //for(long ii = 0; ii < start; ++ii)
            //{
            //    value += increment;
            //}
            
            // INCORRECT, BUT FAST
            long value = (long) (startValue + increment * start);
            for(int startInt = (int) start;
                startInt != startPlusLength;
                ++startInt, offset += stride, value += increment)
            {
                array[offset] = value;
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "LongIlaRamp");
            map.put("length", new Long(length()));
            map.put("startValue", new Long(startValue));
            map.put("increment", new Long(increment));

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
