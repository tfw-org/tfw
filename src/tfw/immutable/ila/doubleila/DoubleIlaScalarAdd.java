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
package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class DoubleIlaScalarAdd
{
    private DoubleIlaScalarAdd()
    {
    	// non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, double scalar)
    {
        Argument.assertNotNull(ila, "ila");

        return new MyDoubleIla(ila, scalar);
    }

    private static class MyDoubleIla extends AbstractDoubleIla
        implements ImmutableProxy
    {
        private final DoubleIla ila;
        private final double scalar;

        MyDoubleIla(DoubleIla ila, double scalar)
        {
            super(ila.length());
                    
            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(double[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] += scalar;
            }
        }
                
        public Map getParameters()
        {
            HashMap map = new HashMap();
                        
            map.put("name", "DoubleIlaScalarAdd");
            map.put("ila", getImmutableInfo(ila));
            map.put("scalar", new Double(scalar));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
