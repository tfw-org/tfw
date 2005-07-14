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
package tfw.immutable.ila.longila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class LongIlaMultiply
{
    private LongIlaMultiply() {}

    public static LongIla create(LongIla leftIla, LongIla rightIla)
    {
    	Argument.assertNotNull(leftIla, "leftIla");
    	Argument.assertNotNull(rightIla, "rightIla");
    	Argument.assertEquals(leftIla.length(), rightIla.length(),
    		"leftIla.length()", "rightIla.length()");

		return new MyLongIla(leftIla, rightIla);
    }

    private static class MyLongIla extends AbstractLongIla
    	implements ImmutableProxy
    {
		private LongIla leftIla;
		private LongIla rightIla;

		MyLongIla(LongIla leftIla, LongIla rightIla)
		{
		    super(leftIla.length());
		    
		    this.leftIla = leftIla;
		    this.rightIla = rightIla;
		}

		protected void toArrayImpl(long[] array, int offset,
			long start, int length) throws DataInvalidException
		{
		    LongIlaIterator li = new LongIlaIterator(
		    	LongIlaSegment.create(leftIla, start, length));
		    LongIlaIterator ri = new LongIlaIterator(
		    	LongIlaSegment.create(rightIla, start, length));
		    
		    for (int i=0 ; li.hasNext() ; i++)
		    {
		    	array[offset+i] = li.next() * ri.next();
		    }
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "LongIlaMultiply");
			map.put("leftIla", getImmutableInfo(leftIla));
			map.put("rightIla", getImmutableInfo(rightIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}