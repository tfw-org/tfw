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

/**
 *
 * @immutables.types=all
 */
public class StringIlaFromArrayTest extends TestCase
{
    public void testImmutabilityCheck()
        throws Exception
    {
        final int ilaLength = IlaTestDimensions.defaultIlaLength();
        
        final String[] creation = new String[ilaLength];
        for(int ii = 0; ii < creation.length; ++ii)
        {
            creation[ii] = new String();
        }
        final StringIla ila = StringIlaFromArray.create(creation);
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final String epsilon = "";
        StringIlaCheck.checkWithoutCorrectness(ila, offsetLength, epsilon);
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
        
        final String[] creation = new String[ilaLength];
        for(int ii = 0; ii < creation.length; ++ii)
        {
            creation[ii] = new String();
        }
        final StringIla ila = StringIlaFromArray.create(creation);

        for(int stride = -maxAbsStride; stride <= maxAbsStride; ++stride)
        {
            if(stride != 0)
            {
                int absStride = stride < 0 ? -stride : stride;
                int offsetStart = stride < 0 ? (ilaLength - 1) * absStride : 0;
                int offsetEnd = offsetStart + addlOffsetLength;
                for(int offset = offsetStart; offset < offsetEnd; ++offset)
                {
                    final String[] arrayBase = new String
                        [(ilaLength - 1) * absStride + 1 + addlOffsetLength];
                    final String[] ilaBase = new String
                        [(ilaLength - 1) * absStride + 1 + addlOffsetLength];
                    for(int length = 1; length <= ilaLength; ++length)
                    {
                        for(long start = 0; start < ilaLength - length + 1;
                            ++start)
                        {
                            for(int ii = 0; ii < arrayBase.length; ++ii)
                            {
                                arrayBase[ii] = ilaBase[ii]
                                    = new String();
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
