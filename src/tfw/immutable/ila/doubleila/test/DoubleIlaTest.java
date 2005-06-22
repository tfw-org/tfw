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
package tfw.immutable.ila.doubleila.test;

import	java.util.Arrays;
import	tfw.immutable.ila.doubleila.DoubleIla;

public class DoubleIlaTest
{
	private DoubleIlaTest() {}
	
	public static String check(DoubleIla ila1, DoubleIla ila2)
	{
		if (ila1.length() != ila2.length())
		{
			return("ila1.length() (="+ila1.length()+
				") != ila2.length() (="+ila2.length()+")");
		}
		
		int length = (int)ila1.length();
		
		// Check to see if the zero argument toArray() methods return
		// the same result.
		
		double[] o1 = ila1.toArray();
		double[] o2 = ila2.toArray();
		
		if (!Arrays.equals(o1, o2))
		{
			return("ila1.toArray() != ila2.toArray()");
		}
		
		// Check to see if the two argument toArray() methods return
		// the same result.
		
		for (long s=0 ; s < length ; s++)
		{
			int remainingLength = length - (int)s;
			
			for (int l=0 ; l < remainingLength ; l++)
			{
				o1 = ila1.toArray(s, l);
				o2 = ila2.toArray(s, l);
				
				if (!Arrays.equals(o1, o2))
				{
					return("ila1.toArray(start="+s+", length="+l+
						") != ila2.toArray(start="+s+", length="+l+")");
				}
			}
		}
		
		// Check to see if the four argument toArray() methods return
		// the same result.
		
		for (int s=0 ; s < length ; s++)
		{
			for (int l=0 ; l < length - s ; l++)
			{
				for (int o=0 ; o < length - l ; o++)
				{
					o1 = new double[length];
					o2 = new double[length];

					ila1.toArray(o1, o, s, l);
					ila2.toArray(o2, o, s, l);
					
					if (!Arrays.equals(o1, o2))
					{
						return("ila1.toArray(array, offset="+o+
								", start="+s+", length="+l+
								") != ila2.toArray(array, offset="+o+
								", start="+s+", length="+l+")");
					}
				}
			}
		}

		return(null);
	}
}