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

package tfw.immutable.ilm.objectilm.test;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.objectilm.ObjectIlm;

public final class ObjectIlmCheck
{
	private ObjectIlmCheck() {}
	
	public static void check(ObjectIlm ilm1, ObjectIlm ilm2)
	{
		Argument.assertEquals(ilm1.width(), ilm2.width(),
			"ilm1.width()", "ilm2.width");
		Argument.assertEquals(ilm1.height(), ilm2.height(),
			"ilm1.height()", "ilm2.height()");
			
		// Check to see if the zero argument toArray() methods return
		// the same result.
		
		final int width = (int)ilm1.width();
		final int height = (int)ilm1.height();
		Object[][] a1 = null;
		Object[][] a2 = null;
		
		try
		{
			a1 = ilm1.toArray();
			a2 = ilm2.toArray();
		}
		catch(DataInvalidException die)
		{
			throw new IllegalArgumentException(
				die.toString());
		}
		
		for (int r=0 ; r < height ; r++)
		{
			for (int c=0 ; c < width ; c++)
			{
				if (a1[r][c] != a2[r][c])
				{
					throw new IllegalArgumentException(
						"zero argument: ilm1.toArray()["+r+"]["+c+
						"] != ilm2.toArray()["+r+"]["+c+"]");
				}
			}
		}
		
		// Check to see if the four argument toArray() methods return
		// the same result.

		for (int sr=0 ; sr < height ; sr++)
		{
			for (int sc=0 ; sc < width ; sc++)
			{
				int remainingHeight = height - sr;
				int remainingWidth = width - sc;
				
				for (int h=0 ; h < remainingHeight ; h++)
				{
					for (int w=0 ; w < remainingWidth ; w++)
					{
						try
						{
							a1 = ilm1.toArray(sr, sc, w, h);
							a2 = ilm2.toArray(sr, sc, w, h);
						}
						catch(DataInvalidException die)
						{
							throw new IllegalArgumentException(
								die.toString());
						}
						
						for (int r=0 ; r < h ; r++)
						{
							for (int c=0 ; c < w ; c++)
							{
								if (a1[r][c] != a2[r][c])
								{
									throw new IllegalArgumentException(
										"four argument: ilm1.toArray("+
										sr+", "+sc+", "+w+", "+h+")["+r+"]["+c+
										"] != ilm2.toArray("+
										sr+", "+sc+", "+w+", "+h+")["+r+"]["+c+"]");
								}
							}
						}
					}
				}
			}
		}
	}
}