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
package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class FloatIlaMultiply
{
    private FloatIlaMultiply() {}

    public static FloatIla create(FloatIla leftIla, FloatIla rightIla)
    {
    	Argument.assertNotNull(leftIla, "leftIla");
    	Argument.assertNotNull(rightIla, "rightIla");
    	Argument.assertEquals(leftIla.length(), rightIla.length(),
    		"leftIla.length()", "rightIla.length()");

		return new MyFloatIla(leftIla, rightIla);
    }

    private static class MyFloatIla extends AbstractFloatIla
    	implements ImmutableProxy
    {
		private FloatIla leftIla;
		private FloatIla rightIla;

		MyFloatIla(FloatIla leftIla, FloatIla rightIla)
		{
		    super(leftIla.length());
		    
		    this.leftIla = leftIla;
		    this.rightIla = rightIla;
		}

		protected void toArrayImpl(float[] array, int offset,
			long start, int length) throws DataInvalidException
		{
		    FloatIlaIterator li = new FloatIlaIterator(
		    	FloatIlaSegment.create(leftIla, start, length));
		    FloatIlaIterator ri = new FloatIlaIterator(
		    	FloatIlaSegment.create(rightIla, start, length));
		    
		    for (int i=0 ; li.hasNext() ; i++)
		    {
		    	array[offset+i] = li.next() * ri.next();
		    }
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "FloatIlaMultiply");
			map.put("leftIla", getImmutableInfo(leftIla));
			map.put("rightIla", getImmutableInfo(rightIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}