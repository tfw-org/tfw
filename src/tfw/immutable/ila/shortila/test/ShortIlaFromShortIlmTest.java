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

package tfw.immutable.ila.shortila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;
import tfw.immutable.ila.shortila.ShortIlaFromShortIlm;
import tfw.immutable.ilm.shortilm.ShortIlm;
import tfw.immutable.ilm.shortilm.ShortIlmFromArray;

public class ShortIlaFromShortIlmTest extends TestCase
{
	public void testShortIlaFromShortIlm()
	{
		final Random random = new Random();
		final int WIDTH = 11;
		final int HEIGHT = 7;
		final int LENGTH = WIDTH * HEIGHT;
	
		short[][] ilmArray = new short[HEIGHT][WIDTH];
		short[] ilaArray = new short[LENGTH];
	
		for (int r=0 ; r < HEIGHT ; r++)
		{
			for (int c=0 ; c < WIDTH ; c++)
			{
				final short element = (short)random.nextInt();
				
				ilmArray[r][c] = element;
				ilaArray[r * WIDTH + c] = element;
			}
		}
		
		ShortIlm ilm = ShortIlmFromArray.create(ilmArray);
		ShortIla ila = ShortIlaFromArray.create(ilaArray);
		
		try
		{
			ShortIlaFromShortIlm.create(null);
			fail("ilm == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s = ShortIlaCheck.check(ila,
			ShortIlaFromShortIlm.create(ilm));
		
		assertNull(s, s);
	}
}
