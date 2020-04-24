/*
 * The Framework Project
 * Copyright (C) 2006 Anonymous
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
package tfw.audio;

import java.util.Arrays;
import junit.framework.TestCase;
import tfw.audio.byteila.MuLawByteIlaFromLinearShortIla;
import tfw.audio.shortila.LinearShortIlaFromMuLawByteIla;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

public class MuLawTest extends TestCase
{
	public void testMuLaw() throws Exception
	{
		short[] linearArray = new short[65536];
		
		for (int i=0 ; i < linearArray.length ; i++)
		{
			linearArray[i] = (short)(i + (int)Short.MIN_VALUE);
		}
		
		ShortIla firstLinearIla = ShortIlaFromArray.create(linearArray);
		
		ByteIla firstMuLawIla = MuLawByteIlaFromLinearShortIla.create(
			firstLinearIla);
		
		ShortIla secondLinearIla = LinearShortIlaFromMuLawByteIla.create(
			firstMuLawIla);
		
		ByteIla secondMuLawIla = MuLawByteIlaFromLinearShortIla.create(
			secondLinearIla);
		
		byte[] firstMuLawArray = firstMuLawIla.toArray();
		byte[] secondMuLawArray = secondMuLawIla.toArray();
		
		assertTrue(Arrays.equals(firstMuLawArray, secondMuLawArray));
	}
}
