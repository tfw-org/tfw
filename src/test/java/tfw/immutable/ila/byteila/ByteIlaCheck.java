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
package tfw.immutable.ila.byteila;

import tfw.immutable.ila.byteila.ByteIla;
import java.util.Random;

/**
 *
 * @immutables.types=numeric
 */
public final class ByteIlaCheck
{
    private ByteIlaCheck()
    {
        // non-instantiable class
    }

    public static void checkAll(ByteIla target, ByteIla actual,
                                int addlOffsetLength, int maxAbsStride,
                                byte epsilon)
        throws Exception
    {
        checkZeroArgImmutability(actual);
        checkTwoArgImmutability(actual, epsilon);
        checkTwoFourEquivalence(actual, epsilon);
        checkFourFiveEquivalence(actual, addlOffsetLength, epsilon);
        checkCorrectness(target, actual, addlOffsetLength,
                         maxAbsStride, epsilon);
    }

    public static void checkWithoutCorrectness(ByteIla ila,
                                               int offsetLength,
                                               byte epsilon)
        throws Exception
    {
        checkZeroArgImmutability(ila);
        checkTwoArgImmutability(ila, epsilon);
        checkTwoFourEquivalence(ila, epsilon);
        checkFourFiveEquivalence(ila, offsetLength, epsilon);
    }

    public static void checkZeroArgImmutability(ByteIla ila)
        throws Exception
    {
        final long firstLength = ila.length();
        final byte[] firstArray = ila.toArray();
        final long secondLength = ila.length();
        final byte[] secondArray = ila.toArray();
        final long thirdLength = ila.length();
        final byte[] thirdArray = ila.toArray();
        final long fourthLength = ila.length();

        if(firstArray.length != firstLength)
            throw new Exception("firstArray.length != firstLength");
        if(secondArray.length != secondLength)
            throw new Exception("secondArray.length != secondLength");
        if(thirdArray.length != thirdLength)
            throw new Exception("thirdArray.length != thirdLength");

        if(firstLength != secondLength)
            throw new Exception("firstLength != secondLength");
        if(secondLength != thirdLength)
            throw new Exception("secondLength != thirdLength");
        if(thirdLength != fourthLength)
            throw new Exception("thirdLength != fourthLength");

        final Random random = new Random(0);

        for(int ii = 0; ii < firstLength; ++ii)
        {
            secondArray[ii] = (byte)random.nextInt();
        }

        for(int ii = 0; ii < firstLength; ++ii)
        {
            if(firstArray[ii] != thirdArray[ii])
                throw new Exception("firstArray[" + ii + "] ("
                                    + firstArray[ii] + ") != thirdArray["
                                    + ii + "] (" + thirdArray[ii] + ")");
        }
    }

    // also performs zero-two equivalence
    public static void checkTwoArgImmutability(ByteIla ila,
                                               byte epsilon)
        throws Exception
    {
        final byte eps = epsilon < 0.0 ? (byte) -epsilon : epsilon;
        final byte neps = (byte) -eps;
        final int ilaLength = ila.length() <= Integer.MAX_VALUE
            ? (int) ila.length() : Integer.MAX_VALUE;
        final byte[] baseline = ila.toArray(0, ilaLength);
        if(baseline.length != ilaLength)
            throw new Exception("baseline.length != ilaLength");
        for(int length = 1; length <= ilaLength; ++length)
        {
            for(long start = 0; start < ilaLength - length + 1; ++start)
            {
                final byte[] subset = ila.toArray(start, length);
                if(subset.length != length)
                    throw new Exception("subset.length != length");
                for(int ii = 0; ii < subset.length; ++ii)
                {
                    byte delta = (byte)
                          (baseline[ii + (int) start] - subset[ii]);
                    if(!(neps <= delta && delta <= eps))
                        throw new Exception("subset[" + ii + "] ("
                                            + subset[ii] + ") !~ baseline["
                                            + (ii + start) + "] ("
                                            + baseline[ii + (int) start]
                                            + ") {length=" + length
                                            + ",start=" + start + "}");
                }
            }
        }
    }

    public static void checkTwoFourEquivalence(ByteIla ila,
                                               byte epsilon)
        throws Exception
    {
        final byte eps = epsilon < 0.0 ? (byte) -epsilon : epsilon;
        final byte neps = (byte) -eps;
        final int ilaLength = ila.length() <= Integer.MAX_VALUE
            ? (int) ila.length() : Integer.MAX_VALUE;
        final byte[] four = new byte[ilaLength];
        for(int length = 1; length <= ilaLength; ++length)
        {
            for(long start = 0; start < ilaLength - length + 1; ++start)
            {
                final byte[] two = ila.toArray(start, length);
                ila.toArray(four, 0, start, length);
                for(int ii = 0; ii < length; ++ii)
                {
                    byte delta = (byte) (four[ii] - two[ii]);
                    if(!(neps <= delta && delta <= eps))
                        throw new Exception("four[" + ii + "] ("
                                            + four[ii] + ") !~ two["
                                            + ii + "] ("
                                            + two[ii]
                                            + ") {length=" + length
                                            + ",start=" + start + "}");
                }
            }
        }
    }

    public static void checkFourFiveEquivalence(ByteIla ila, 
                                                int offsetLength,
                                                byte epsilon)
        throws Exception
    {
        if(offsetLength < 0)
            throw new Exception("offsetLength < 0 not allowed");
        final byte eps = epsilon < 0.0 ? (byte) -epsilon : epsilon;
        final byte neps = (byte) -eps;
        final Random random = new Random(0);
        final int ilaLength
            = ila.length() + offsetLength <= Integer.MAX_VALUE
            ? (int) ila.length() : Integer.MAX_VALUE - offsetLength;
        for(int offset = 0; offset < offsetLength; ++offset)
        {
            final byte[] four = new byte[ilaLength + offsetLength];
            final byte[] five = new byte[ilaLength + offsetLength];
            for(int length = 1; length <= ilaLength; ++length)
            {
                for(long start = 0; start < ilaLength - length + 1;
                    ++start)
                {
                    for(int ii = 0; ii < four.length; ++ii)
                    {
                        five[ii] = four[ii] = (byte)random.nextInt();
                    }
                    ila.toArray(four, offset, start, length);
                    ila.toArray(five, offset, 1, start, length);
                    for(int ii = 0; ii < length; ++ii)
                    {
                        byte delta = (byte) (four[ii] - five[ii]);
                        if(!(neps <= delta && delta <= eps))
                            throw new Exception("four[" + ii + "] ("
                                                + four[ii] + ") !~ five["
                                                + ii + "] ("
                                                + five[ii]
                                                + ") {length=" + length
                                                + ",start=" + start
                                                + ",offset=" + offset
                                                + "}");
                    }
                }
            }
        }
    }

    public static void checkCorrectness(ByteIla target, ByteIla actual,
                                        int addlOffsetLength, int maxAbsStride,
                                        byte epsilon)
        throws Exception
    {
        if(addlOffsetLength < 0)
            throw new Exception("addlOffsetLength < 0 not allowed");
        if(maxAbsStride < 1)
            throw new Exception("maxAbsStride < 1 not allowed");
        if(target.length() != actual.length())
            throw new Exception("target.length() != actual.length()");
        final byte eps = epsilon < 0.0 ? (byte) -epsilon : epsilon;
        final byte neps = (byte) -eps;
        final Random random = new Random(0);
        final int ilaLength = target.length() + addlOffsetLength
            <= Integer.MAX_VALUE
            ? (int) target.length() : Integer.MAX_VALUE - addlOffsetLength;
        for(int stride = -maxAbsStride; stride <= maxAbsStride; ++stride)
        {
            if(stride != 0)
            {
                int absStride = stride < 0 ? -stride : stride;
                int offsetStart = stride < 0 ?
                    (ilaLength - 1) * absStride : 0;
                int offsetEnd = offsetStart + addlOffsetLength;
                for(int offset = offsetStart; offset < offsetEnd; ++offset)
                {
                    final int arraySize = (ilaLength - 1) * absStride
                        + 1 + addlOffsetLength;
                    final byte[] targetBase = new byte[arraySize];
                    final byte[] actualBase = new byte[arraySize];
                    for(int length = 1; length <= ilaLength; ++length)
                    {
                        for(long start = 0; start < ilaLength - length + 1;
                            ++start)
                        {
                            for(int ii = 0; ii < targetBase.length; ++ii)
                            {
                                targetBase[ii] = actualBase[ii]
                                    = (byte)random.nextInt();
                            }
                            target.toArray(targetBase, offset, stride,
                                           start, length);
                            actual.toArray(actualBase, offset, stride,
                                           start, length);
                            for(int ii = 0; ii < arraySize; ++ii)
                            {
                                byte delta = (byte)
                                      (actualBase[ii] - targetBase[ii]);
                                if(!(neps <= delta && delta <= eps))
                                    throw new Exception("actual[" + ii
                                                        + "] ("
                                                        + actualBase[ii]
                                                        + ") !~ target["
                                                        + ii + "] ("
                                                        + targetBase[ii]
                                                        + ") {length="
                                                        + length
                                                        + ",start=" + start
                                                        + ",offset="
                                                        + offset
                                                        + ",stride="
                                                        + stride
                                                        + "}");
                            }
                        }
                    }
                }
            }
        }
    }

    public static void dump(String msg, byte[] array)
    {
        System.out.println(msg + ":");
        for(int ii = 0; ii < array.length; ++ii)
        {
            System.out.print(" " + array[ii]);
        }
        System.out.println();
    }
}
// AUTO GENERATED FROM TEMPLATE
