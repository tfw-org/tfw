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

package tfw.immutable.ila.charila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;
import tfw.immutable.ila.charila.CharIlaFromCharIlm;
import tfw.immutable.ilm.charilm.CharIlm;
import tfw.immutable.ilm.charilm.CharIlmFromArray;

public class CharIlaFromCharIlmTest extends TestCase
{
	public void testCharIlaFromCharIlm()
	{
		final Random random = new Random();
		final int WIDTH = 11;
		final int HEIGHT = 7;
		final int LENGTH = WIDTH * HEIGHT;
	
		char[][] ilmArray = new char[HEIGHT][WIDTH];
		char[] ilaArray = new char[LENGTH];
	
		for (int r=0 ; r < HEIGHT ; r++)
		{
			for (int c=0 ; c < WIDTH ; c++)
			{
				final char element = (char)random.nextInt();
				
				ilmArray[r][c] = element;
				ilaArray[r * WIDTH + c] = element;
			}
		}
		
		CharIlm ilm = CharIlmFromArray.create(ilmArray);
		CharIla ila = CharIlaFromArray.create(ilaArray);
		
		try
		{
			CharIlaFromCharIlm.create(null);
			fail("ilm == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s = CharIlaCheck.check(ila,
			CharIlaFromCharIlm.create(ilm));
		
		assertNull(s, s);
	}
}
