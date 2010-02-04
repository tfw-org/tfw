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
package tfw.immutable.ila.charila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

/**
 *
 * @immutables.types=numericnotlong
 */
public final class CharIlaFromCastLongIla
{
    private CharIlaFromCastLongIla()
    {
    	// non-instantiable class
    }

    public static CharIla create(LongIla longIla)
    {
        return create(longIla, LongIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static CharIla create(LongIla longIla, int bufferSize)
    {
        Argument.assertNotNull(longIla, "longIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyCharIla(longIla, bufferSize);
    }

    private static class MyCharIla extends AbstractCharIla
        implements ImmutableProxy
    {
        private final LongIla longIla;
        private final int bufferSize;

        MyCharIla(LongIla longIla, int bufferSize)
        {
            super(longIla.length());
                    
            this.longIla = longIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(char[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            LongIlaIterator fi = new LongIlaIterator(
                LongIlaSegment.create(longIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (char) fi.next();
            }
        }
                
        public Map getParameters()
        {
            HashMap map = new HashMap();
                        
            map.put("name", "CharIlaFromCastLongIla");
            map.put("longIla", getImmutableInfo(longIla));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
