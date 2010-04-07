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
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class FloatIlaSubtract
{
    private FloatIlaSubtract()
    {
    	// non-instantiable class
    }

    public static FloatIla create(FloatIla leftIla, FloatIla rightIla)
    {
    	return create(leftIla, rightIla, FloatIlaIterator.DEFAULT_BUFFER_SIZE);
    }
    
    public static FloatIla create(FloatIla leftIla, FloatIla rightIla,
        int bufferSize)
    {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(),
                              "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyFloatIla(leftIla, rightIla, bufferSize);
    }

    private static class MyFloatIla extends AbstractFloatIla
        implements ImmutableProxy
    {
        private final FloatIla leftIla;
        private final FloatIla rightIla;
        private final int bufferSize;

        MyFloatIla(FloatIla leftIla, FloatIla rightIla, int bufferSize)
        {
            super(leftIla.length());
                    
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(float[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            FloatIlaIterator li = new FloatIlaIterator(
                FloatIlaSegment.create(leftIla, start, length), bufferSize);
            FloatIlaIterator ri = new FloatIlaIterator(
                FloatIlaSegment.create(rightIla, start, length), bufferSize);

            for (int ii = offset; li.hasNext(); ii += stride)
            {
                array[ii] = (float) (li.next() - ri.next());
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "FloatIlaSubtract");
            map.put("leftIla", getImmutableInfo(leftIla));
            map.put("rightIla", getImmutableInfo(rightIla));
            map.put("bufferSize", new Integer(bufferSize));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
