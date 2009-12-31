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
package tfw.audio.test;

import java.util.Arrays;
import junit.framework.TestCase;
import tfw.audio.au.Au;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;

public class AuTest extends TestCase
{
	public void testAu() throws Exception
	{
		byte[] auBytes1 = new byte[] {
			 46, 115, 110, 100,   0,   0,   0,  32,   0,   0,   0,  32,
			  0,   0,   0,   2,   0,   0,  31,  64,   0,   0,   0,   2,
			  0,   0,   0,   0,   0,   0,   0,   0, -15, -14, -13, -12,
			-11, -10,  -9,  -8,  -7,  -6,  -5,  -4,  -3,  -2,  -1,   0,
			  0,   1,   2,   3,   4,   5,   6,   7,   8,   9,  10,  11,
			 12,  13,  14,  15};
		
		ByteIla auByteIla = ByteIlaFromArray.create(auBytes1);
		
		Au au1 = new Au(auByteIla);

		Au au2 = new Au(au1.encoding, au1.sampleRate, au1.numberOfChannels,
			au1.annotation, au1.audioData);
		
		byte[] auBytes2 = au2.auByteData.toArray();
		
		assertTrue("auBytes1 != auBytes2",
			Arrays.equals(auBytes1, auBytes2));
	}
}
