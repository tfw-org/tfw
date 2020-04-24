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

package tfw.immutable.ila.shortila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

/**
 *
 * @immutables.types=all
 */
public class ShortIlaFromArrayTest extends TestCase
{
    public void testImmutabilityCheck()
        throws Exception
    {
        final int ilaLength = IlaTestDimensions.defaultIlaLength();
        final Random random = new Random(0);
        final short[] creation = new short[ilaLength];
        for(int ii = 0; ii < creation.length; ++ii)
        {
            creation[ii] = (short)random.nextInt();
        }
        final ShortIla ila = ShortIlaFromArray.create(creation);
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final short epsilon = (short)0;
        ShortIlaCheck.checkWithoutCorrectness(ila, offsetLength, epsilon);
    }

    public void testValueCorrectness()
        throws Exception
    {
        final int ilaLength =
            IlaTestDimensions.defaultIlaLength();
        final int addlOffsetLength =
            IlaTestDimensions.defaultOffsetLength();
        final int maxAbsStride =
            IlaTestDimensions.defaultMaxStride();
        final Random random = new Random(0);
        final short[] creation = new short[ilaLength];
        for(int ii = 0; ii < creation.length; ++ii)
        {
            creation[ii] = (short)random.nextInt();
        }
        final ShortIla ila = ShortIlaFromArray.create(creation);

        for(int stride = -maxAbsStride; stride <= maxAbsStride; ++stride)
        {
            if(stride != 0)
            {
                int absStride = stride < 0 ? -stride : stride;
                int offsetStart = stride < 0 ? (ilaLength - 1) * absStride : 0;
                int offsetEnd = offsetStart + addlOffsetLength;
                for(int offset = offsetStart; offset < offsetEnd; ++offset)
                {
                    final short[] arrayBase = new short
                        [(ilaLength - 1) * absStride + 1 + addlOffsetLength];
                    final short[] ilaBase = new short
                        [(ilaLength - 1) * absStride + 1 + addlOffsetLength];
                    for(int length = 1; length <= ilaLength; ++length)
                    {
                        for(long start = 0; start < ilaLength - length + 1;
                            ++start)
                        {
                            for(int ii = 0; ii < arrayBase.length; ++ii)
                            {
                                arrayBase[ii] = ilaBase[ii]
                                    = (short)random.nextInt();
                            }
                            for(int ii = (int) start, index = offset;
                                ii < start + length;
                                ++ii, index += stride)
                            {
                                arrayBase[index] = creation[ii];
                            }
                            ila.toArray(ilaBase, offset, stride,
                                        start, length);
                            for(int ii = 0; ii < arrayBase.length; ++ii)
                            {
                                if(arrayBase[ii] != ilaBase[ii])
                                    throw new Exception("actual[" + ii + "] ("
                                                        + ilaBase[ii]
                                                        + ") !~ target["
                                                        + ii + "] ("
                                                        + arrayBase[ii]
                                                        + ") {length="
                                                        + length
                                                        + ",start=" + start
                                                        + ",offset=" + offset
                                                        + ",stride=" + stride
                                                        + "}");
                            }
                        }
                    }
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
