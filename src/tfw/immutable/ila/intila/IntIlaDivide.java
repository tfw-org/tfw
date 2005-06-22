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
 * witout even the implied warranty of
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
package tfw.immutable.ila.intila;

import tfw.check.Argument;

public final class IntIlaDivide
{
    private IntIlaDivide() {}

    public static IntIla create(IntIla leftIla, IntIla rightIla)
    {
    	Argument.assertNotNull(leftIla, "leftIla");
    	Argument.assertNotNull(rightIla, "rightIla");
    	Argument.assertEquals(leftIla.length(), rightIla.length(),
    		"leftIla.length()", "rightIla.length()");

		return new MyIntIla(leftIla, rightIla);
    }

    private static class MyIntIla extends AbstractIntIla
    {
		private IntIla leftIla;
		private IntIla rightIla;

		MyIntIla(IntIla leftIla, IntIla rightIla)
		{
		    super(leftIla.length());
		    
		    this.leftIla = leftIla;
		    this.rightIla = rightIla;
		}

		protected void toArrayImpl(int[] array, int offset,
			long start, int length)
		{
		    IntIlaIterator li = new IntIlaIterator(
		    	IntIlaSegment.create(leftIla, start, length));
		    IntIlaIterator ri = new IntIlaIterator(
		    	IntIlaSegment.create(rightIla, start, length));
		    
		    for (int i=0 ; li.hasNext() ; i++)
		    {
		    	array[offset+i] = li.next() / ri.next();
		    }
		}
    }
}
