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
package tfw.immutable.ila.booleanila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class BooleanIlaConcatenate
{
    private BooleanIlaConcatenate()
    {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla leftIla, BooleanIla rightIla)
    {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");

        /*
          // this efficiency step would help out in a number
          // of situations, but could be confusing when the
          // immutable proxy getParameters() is called and you
          // don't see your concatenation!
        if(leftIla.length() == 0)
            return rightIla;
        if(rightIla.length() == 0)
            return leftIla;
        */
        return new MyBooleanIla(leftIla, rightIla);
    }

    private static class MyBooleanIla extends AbstractBooleanIla
        implements ImmutableProxy
    {
        private final BooleanIla leftIla;
        private final BooleanIla rightIla;
        private final long leftIlaLength;

        MyBooleanIla(BooleanIla leftIla, BooleanIla rightIla)
        {
            super(leftIla.length() + rightIla.length());
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.leftIlaLength = leftIla.length();
        }

        protected void toArrayImpl(boolean[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            if(start + length <= leftIlaLength)
            {
                leftIla.toArray(array, offset, stride, start, length);
            }
            else if(start >= leftIlaLength)
            {
                rightIla.toArray(array, offset, stride, start - leftIlaLength,
                                 length);
            }
            else
            {
                final int leftAmount = (int) (leftIlaLength - start);
                leftIla.toArray(array, offset, stride, start, leftAmount);
                rightIla.toArray(array, offset + leftAmount * stride,
                                 stride, 0, length - leftAmount);
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "BooleanIlaConcatenate");
            map.put("length", new Long(length()));
            map.put("leftIla", getImmutableInfo(leftIla));
            map.put("rightIla", getImmutableInfo(rightIla));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
