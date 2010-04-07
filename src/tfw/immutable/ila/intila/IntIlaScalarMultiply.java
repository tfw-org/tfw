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

/**
 *
 * @immutables.types=numeric
 */
public final class IntIlaScalarMultiply
{
    private IntIlaScalarMultiply()
    {
    	// non-instantiable class
    }

    public static IntIla create(IntIla ila, int scalar)
    {
        Argument.assertNotNull(ila, "ila");

        return new MyIntIla(ila, scalar);
    }

    private static class MyIntIla extends AbstractIntIla
        implements ImmutableProxy
    {
        private final IntIla ila;
        private final int scalar;

        MyIntIla(IntIla ila, int scalar)
        {
            super(ila.length());
                    
            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(int[] array, int offset,
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
                        
            map.put("name", "IntIlaScalarMultiply");
            map.put("ila", getImmutableInfo(ila));
            map.put("scalar", new Integer(scalar));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
