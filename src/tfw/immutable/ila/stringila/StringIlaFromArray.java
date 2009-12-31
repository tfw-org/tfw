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
package tfw.immutable.ila.stringila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class StringIlaFromArray
{
    private StringIlaFromArray() {}

    public static StringIla create(String[] array)
    {
    	Argument.assertNotNull(array, "array");

		return new MyStringIla(array);
    }

    private static class MyStringIla extends AbstractStringIla
    	implements ImmutableProxy
    {
		private final String[] array;

		MyStringIla(String[] array)
		{
		    super(array.length);
		    
		    this.array = (String[])array.clone();
		}

		protected void toArrayImpl(String[] array, int offset,
			long start, int length)
		{
		    System.arraycopy(this.array, (int) start, array, offset, length);
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "StringIlaFromArray");
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}