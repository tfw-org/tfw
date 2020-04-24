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

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;
import tfw.immutable.ila.charila.CharIlaInterleave;

/**
 * 
 * @immutables.types=all
 */
public class CharIlaInterleaveTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj)
        {
            final char[][] target = new char[jj][length];
            final char[] array = new char[jj * length];
            for (int ii = 0; ii < jj * length; ++ii)
            {
                array[ii] = target[ii % jj][ii / jj] = (char)random.nextInt();
            }
            CharIla[] ilas = new CharIla[jj];
            for (int ii = 0; ii < jj; ++ii)
            {
                ilas[ii] = CharIlaFromArray.create(target[ii]);
            }
            CharIla targetIla = CharIlaFromArray.create(array);
            CharIla actualIla = CharIlaInterleave.create(ilas);
            final char epsilon = (char)0;
            CharIlaCheck.checkAll(targetIla, actualIla,
                                      IlaTestDimensions.defaultOffsetLength(),
                                      IlaTestDimensions.defaultMaxStride(),
                                      epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
