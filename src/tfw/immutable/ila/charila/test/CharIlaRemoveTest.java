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

package tfw.immutable.ila.charila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.test.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;
import tfw.immutable.ila.charila.CharIlaRemove;

/**
 *
 * @immutables.types=all
 */
public class CharIlaRemoveTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final char[] target = new char[length-1];
        for(int index = 0; index < length; ++index)
        {
            int targetii = 0;
            for(int ii = 0; ii < array.length; ++ii)
            {
                array[ii] = (char)random.nextInt();
                if(ii != index)
                {
                    target[targetii++] = array[ii];
                }
            }
            CharIla origIla = CharIlaFromArray.create(array);
            CharIla targetIla = CharIlaFromArray.create(target);
            CharIla actualIla = CharIlaRemove.create(origIla, index);
            final char epsilon = (char)0;
            CharIlaCheck.checkAll(targetIla, actualIla,
                                      IlaTestDimensions.defaultOffsetLength(),
                                      IlaTestDimensions.defaultMaxStride(),
                                      epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
