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

package tfw.immutable.ila.objectila.test;

import junit.framework.TestCase;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIlaFromObjectIlm;
import tfw.immutable.ila.test.IlaTestDimensions;
import tfw.immutable.ilm.objectilm.ObjectIlm;
import tfw.immutable.ilm.objectilm.ObjectIlmFromArray;

public class ObjectIlaFromObjectIlmTest extends TestCase
{
	public void testObjectIlaFromObjectIlm() throws Exception
	{
		final int WIDTH = 11;
		final int HEIGHT = 7;
		final int LENGTH = WIDTH * HEIGHT;
	
		Object[][] ilmArray = new Object[HEIGHT][WIDTH];
		Object[] ilaArray = new Object[LENGTH];
	
		for (int r=0 ; r < HEIGHT ; r++)
		{
			for (int c=0 ; c < WIDTH ; c++)
			{
				final Object element = new Object();
				
				ilmArray[r][c] = element;
				ilaArray[r * WIDTH + c] = element;
			}
		}
		
		ObjectIlm ilm = ObjectIlmFromArray.create(ilmArray);
		ObjectIla targetIla = ObjectIlaFromArray.create(ilaArray);
		
		try
		{
			ObjectIlaFromObjectIlm.create(null);
			fail("ilm == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		ObjectIla actualIla = ObjectIlaFromObjectIlm.create(ilm);
        final Object epsilon = Object.class;
        ObjectIlaCheck.checkAll(targetIla, actualIla,
                                  IlaTestDimensions.defaultOffsetLength(),
                                  IlaTestDimensions.defaultMaxStride(),
                                  epsilon);
	}
}
