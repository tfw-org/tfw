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

package tfw.immutable.ila.objectila;


import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIlaInterleave;

/**
 * 
 * @immutables.types=all
 */
public class ObjectIlaInterleaveTest extends TestCase
{
    public void testAll() throws Exception
    {
        
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj)
        {
            final Object[][] target = new Object[jj][length];
            final Object[] array = new Object[jj * length];
            for (int ii = 0; ii < jj * length; ++ii)
            {
                array[ii] = target[ii % jj][ii / jj] = new Object();
            }
            ObjectIla[] ilas = new ObjectIla[jj];
            for (int ii = 0; ii < jj; ++ii)
            {
                ilas[ii] = ObjectIlaFromArray.create(target[ii]);
            }
            ObjectIla targetIla = ObjectIlaFromArray.create(array);
            ObjectIla actualIla = ObjectIlaInterleave.create(ilas);
            final Object epsilon = Object.class;
            ObjectIlaCheck.checkAll(targetIla, actualIla,
                                      IlaTestDimensions.defaultOffsetLength(),
                                      IlaTestDimensions.defaultMaxStride(),
                                      epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
