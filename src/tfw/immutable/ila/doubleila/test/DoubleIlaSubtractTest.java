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

package tfw.immutable.ila.doubleila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.test.IlaTestDimensions;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;
import tfw.immutable.ila.doubleila.DoubleIlaSubtract;

/**
 *
 * @immutables.types=numeric
 */
public class DoubleIlaSubtractTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] leftArray = new double[length];
        final double[] rightArray = new double[length];
        final double[] array = new double[length];
        for(int ii = 0; ii < leftArray.length; ++ii)
        {
            leftArray[ii] = random.nextDouble();
            rightArray[ii] = random.nextDouble();
            array[ii] = (double) (leftArray[ii] - rightArray[ii]);
        }
        DoubleIla leftIla = DoubleIlaFromArray.create(leftArray);
        DoubleIla rightIla = DoubleIlaFromArray.create(rightArray);
        DoubleIla targetIla = DoubleIlaFromArray.create(array);
        DoubleIla actualIla = DoubleIlaSubtract.create(leftIla, rightIla);
        final double epsilon = (double) 0.0;
        DoubleIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
