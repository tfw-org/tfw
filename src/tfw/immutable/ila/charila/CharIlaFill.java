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
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class CharIlaFill
{
    private CharIlaFill()
    {
        // non-instantiable class
    }

    public static CharIla create(char value, long length)
    {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyCharIla(value, length);
    }

    private static class MyCharIla extends AbstractCharIla
        implements ImmutableProxy
    {
        private final char value;

        MyCharIla(char value, long length)
        {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(char[] array, int offset,
                                   int stride, long start, int length)
        {
            final int startPlusLength = (int) (start + length);
            for(int startInt = (int) start;
                startInt != startPlusLength;
                ++startInt, offset += stride)
            {
                array[offset] = value;
            }
        }
                
        public Map getParameters()
        {
            HashMap map = new HashMap();
                        
            map.put("name", "CharIlaFill");
            map.put("length", new Long(length()));
            map.put("value", new Character(value));

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
