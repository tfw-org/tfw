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

package tfw.immutable.ila.stringila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.stringila.StringIla;
import tfw.immutable.ila.stringila.StringIlaFromArray;
import tfw.immutable.ila.stringila.StringIlaFromStringIlm;
import tfw.immutable.ilm.stringilm.StringIlm;
import tfw.immutable.ilm.stringilm.StringIlmFromArray;

public class StringIlaFromStringIlmTest extends TestCase
{
	public void testStringIlaFromStringIlm()
	{
		final Random random = new Random();
		final int WIDTH = 11;
		final int HEIGHT = 7;
		final int LENGTH = WIDTH * HEIGHT;
	
		String[][] ilmArray = new String[HEIGHT][WIDTH];
		String[] ilaArray = new String[LENGTH];
	
		for (int r=0 ; r < HEIGHT ; r++)
		{
			for (int c=0 ; c < WIDTH ; c++)
			{
				final String element = new String();
				
				ilmArray[r][c] = element;
				ilaArray[r * WIDTH + c] = element;
			}
		}
		
		StringIlm ilm = StringIlmFromArray.create(ilmArray);
		StringIla ila = StringIlaFromArray.create(ilaArray);
		
		try
		{
			StringIlaFromStringIlm.create(null);
			fail("ilm == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s = StringIlaCheck.check(ila,
			StringIlaFromStringIlm.create(ilm));
		
		assertNull(s, s);
	}
}
