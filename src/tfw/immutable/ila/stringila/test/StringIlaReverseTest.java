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

package tfw.immutable.ila.stringila.test;


import junit.framework.TestCase;
import tfw.immutable.ila.test.IlaTestDimensions;
import tfw.immutable.ila.stringila.StringIla;
import tfw.immutable.ila.stringila.StringIlaFromArray;
import tfw.immutable.ila.stringila.StringIlaReverse;

/**
 *
 * @immutables.types=all
 */
public class StringIlaReverseTest extends TestCase
{
    public void testAll() throws Exception
    {
        
        final int length = IlaTestDimensions.defaultIlaLength();
        final String[] array = new String[length];
        final String[] reversed = new String[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = new String();
        }
        for(int ii = 0; ii < reversed.length; ++ii)
        {
            reversed[ii] = array[length - 1 - ii];
        }
        StringIla origIla = StringIlaFromArray.create(array);
        StringIla targetIla = StringIlaFromArray.create(reversed);
        StringIla actualIla = StringIlaReverse.create(origIla);
        final String epsilon = "";
        StringIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
