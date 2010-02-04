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

package tfw.immutable.ila.booleanila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.test.IlaTestDimensions;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;
import tfw.immutable.ila.booleanila.BooleanIlaSegment;

/**
 *
 * @immutables.types=all
 */
public class BooleanIlaSegmentTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] master = new boolean[length];
        for(int ii = 0; ii < master.length; ++ii)
        {
            master[ii] = random.nextBoolean();
        }
        BooleanIla masterIla = BooleanIlaFromArray.create(master);
        BooleanIla checkIla = BooleanIlaSegment.create(masterIla, 0,
                                                         masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final boolean epsilon = false;
        BooleanIlaCheck.checkWithoutCorrectness(checkIla, offsetLength,
                                                 epsilon);
        for(long start = 0; start < length; ++start)
        {
            for(long len = 0; len < length - start; ++len)
            {
                boolean[] array = new boolean[(int) len];
                for(int ii = 0; ii < array.length; ++ii)
                {
                    array[ii] = master[ii + (int) start];
                }
                BooleanIla targetIla = BooleanIlaFromArray.create(array);
                BooleanIla actualIla = BooleanIlaSegment.create(masterIla,
                                                                  start, len);
                BooleanIlaCheck.checkCorrectness(targetIla, actualIla,
                                                  offsetLength, maxStride,
                                                  epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
