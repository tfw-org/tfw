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
package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class DoubleIlaAdd
{
    private DoubleIlaAdd() {}

    public static DoubleIla create(DoubleIla leftIla, DoubleIla rightIla)
    {
    	Argument.assertNotNull(leftIla, "leftIla");
    	Argument.assertNotNull(rightIla, "rightIla");
    	Argument.assertEquals(leftIla.length(), rightIla.length(),
    		"leftIla.length()", "rightIla.length()");

		return new MyDoubleIla(leftIla, rightIla);
    }

    private static class MyDoubleIla extends AbstractDoubleIla
    	implements ImmutableProxy
    {
		private DoubleIla leftIla;
		private DoubleIla rightIla;

		MyDoubleIla(DoubleIla leftIla, DoubleIla rightIla)
		{
		    super(leftIla.length());
		    
		    this.leftIla = leftIla;
		    this.rightIla = rightIla;
		}

		protected void toArrayImpl(double[] array, int offset,
			long start, int length) throws DataInvalidException
		{
		    DoubleIlaIterator li = new DoubleIlaIterator(
		    	DoubleIlaSegment.create(leftIla, start, length));
		    DoubleIlaIterator ri = new DoubleIlaIterator(
		    	DoubleIlaSegment.create(rightIla, start, length));
		    
		    for (int i=0 ; li.hasNext() ; i++)
		    {
		    	array[offset+i] = li.next() + ri.next();
		    }
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "DoubleIlaAdd");
			map.put("leftIla", getImmutableInfo(leftIla));
			map.put("rightIla", getImmutableInfo(rightIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}