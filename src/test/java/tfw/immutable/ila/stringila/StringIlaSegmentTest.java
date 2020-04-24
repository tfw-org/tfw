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

package tfw.immutable.ila.stringila;


import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.stringila.StringIla;
import tfw.immutable.ila.stringila.StringIlaFromArray;
import tfw.immutable.ila.stringila.StringIlaSegment;

/**
 *
 * @immutables.types=all
 */
public class StringIlaSegmentTest extends TestCase
{
    public void testAll() throws Exception
    {
        
        final int length = IlaTestDimensions.defaultIlaLength();
        final String[] master = new String[length];
        for(int ii = 0; ii < master.length; ++ii)
        {
            master[ii] = new String();
        }
        StringIla masterIla = StringIlaFromArray.create(master);
        StringIla checkIla = StringIlaSegment.create(masterIla, 0,
                                                         masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final String epsilon = "";
        StringIlaCheck.checkWithoutCorrectness(checkIla, offsetLength,
                                                 epsilon);
        for(long start = 0; start < length; ++start)
        {
            for(long len = 0; len < length - start; ++len)
            {
                String[] array = new String[(int) len];
                for(int ii = 0; ii < array.length; ++ii)
                {
                    array[ii] = master[ii + (int) start];
                }
                StringIla targetIla = StringIlaFromArray.create(array);
                StringIla actualIla = StringIlaSegment.create(masterIla,
                                                                  start, len);
                StringIlaCheck.checkCorrectness(targetIla, actualIla,
                                                  offsetLength, maxStride,
                                                  epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
