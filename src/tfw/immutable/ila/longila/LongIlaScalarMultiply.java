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
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class LongIlaScalarMultiply
{
    private LongIlaScalarMultiply()
    {
    	// non-instantiable class
    }

    public static LongIla create(LongIla ila, long scalar)
    {
        Argument.assertNotNull(ila, "ila");

        return new MyLongIla(ila, scalar);
    }

    private static class MyLongIla extends AbstractLongIla
        implements ImmutableProxy
    {
        private final LongIla ila;
        private final long scalar;

        MyLongIla(LongIla ila, long scalar)
        {
            super(ila.length());
                    
            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(long[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] *= scalar;
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "LongIlaScalarMultiply");
            map.put("ila", getImmutableInfo(ila));
            map.put("scalar", new Long(scalar));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
