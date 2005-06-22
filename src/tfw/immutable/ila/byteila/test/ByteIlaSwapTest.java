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

package tfw.immutable.ila.byteila.test;

import junit.framework.TestCase;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.byteila.ByteIlaSwap;

public class ByteIlaSwapTest extends TestCase
{
	public void testByteIlaFromLongIla()
	{
		byte[] byteArray = new byte[] {
			(byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03,
			(byte)0x04, (byte)0x05, (byte)0x06, (byte)0x07,
			(byte)0x08, (byte)0x09, (byte)0x0a, (byte)0x0b,
			(byte)0x0c, (byte)0x0d, (byte)0x0e, (byte)0x0f,
			(byte)0x00, (byte)0x10, (byte)0x20, (byte)0x30,
			(byte)0x40, (byte)0x50, (byte)0x60, (byte)0x70,
			(byte)0x80, (byte)0x90, (byte)0xa0, (byte)0xb0,
			(byte)0xc0, (byte)0xd0, (byte)0xe0, (byte)0xf0,
			(byte)0x00, (byte)0x11, (byte)0x22, (byte)0x33,
			(byte)0x44, (byte)0x55, (byte)0x66, (byte)0x77,
			(byte)0x88, (byte)0x99, (byte)0xaa, (byte)0xbb,
			(byte)0xcc, (byte)0xdd, (byte)0xee, (byte)0xff};
		byte[] swap2Array = new byte[] {
			(byte)0x01, (byte)0x00, (byte)0x03, (byte)0x02,
			(byte)0x05, (byte)0x04, (byte)0x07, (byte)0x06,
			(byte)0x09, (byte)0x08, (byte)0x0b, (byte)0x0a,
			(byte)0x0d, (byte)0x0c, (byte)0x0f, (byte)0x0e,
			(byte)0x10, (byte)0x00, (byte)0x30, (byte)0x20,
			(byte)0x50, (byte)0x40, (byte)0x70, (byte)0x60,
			(byte)0x90, (byte)0x80, (byte)0xb0, (byte)0xa0,
			(byte)0xd0, (byte)0xc0, (byte)0xf0, (byte)0xe0,
			(byte)0x11, (byte)0x00, (byte)0x33, (byte)0x22,
			(byte)0x55, (byte)0x44, (byte)0x77, (byte)0x66,
			(byte)0x99, (byte)0x88, (byte)0xbb, (byte)0xaa,
			(byte)0xdd, (byte)0xcc, (byte)0xff, (byte)0xee};
		byte[] swap4Array = new byte[] {
			(byte)0x03, (byte)0x02, (byte)0x01, (byte)0x00,
			(byte)0x07, (byte)0x06, (byte)0x05, (byte)0x04,
			(byte)0x0b, (byte)0x0a, (byte)0x09, (byte)0x08,
			(byte)0x0f, (byte)0x0e, (byte)0x0d, (byte)0x0c,
			(byte)0x30, (byte)0x20, (byte)0x10, (byte)0x00,
			(byte)0x70, (byte)0x60, (byte)0x50, (byte)0x40,
			(byte)0xb0, (byte)0xa0, (byte)0x90, (byte)0x80,
			(byte)0xf0, (byte)0xe0, (byte)0xd0, (byte)0xc0,
			(byte)0x33, (byte)0x22, (byte)0x11, (byte)0x00,
			(byte)0x77, (byte)0x66, (byte)0x55, (byte)0x44,
			(byte)0xbb, (byte)0xaa, (byte)0x99, (byte)0x88,
			(byte)0xff, (byte)0xee, (byte)0xdd, (byte)0xcc};
		byte[] swap8Array = new byte[] {
			(byte)0x07, (byte)0x06, (byte)0x05, (byte)0x04,
			(byte)0x03, (byte)0x02, (byte)0x01, (byte)0x00,
			(byte)0x0f, (byte)0x0e, (byte)0x0d, (byte)0x0c,
			(byte)0x0b, (byte)0x0a, (byte)0x09, (byte)0x08,
			(byte)0x70, (byte)0x60, (byte)0x50, (byte)0x40,
			(byte)0x30, (byte)0x20, (byte)0x10, (byte)0x00,
			(byte)0xf0, (byte)0xe0, (byte)0xd0, (byte)0xc0,
			(byte)0xb0, (byte)0xa0, (byte)0x90, (byte)0x80,
			(byte)0x77, (byte)0x66, (byte)0x55, (byte)0x44,
			(byte)0x33, (byte)0x22, (byte)0x11, (byte)0x00,
			(byte)0xff, (byte)0xee, (byte)0xdd, (byte)0xcc,
			(byte)0xbb, (byte)0xaa, (byte)0x99, (byte)0x88};
				
		ByteIla byteIla = ByteIlaFromArray.create(byteArray);
		ByteIla swap2Ila = ByteIlaFromArray.create(swap2Array);
		ByteIla swap4Ila = ByteIlaFromArray.create(swap4Array);
		ByteIla swap8Ila = ByteIlaFromArray.create(swap8Array);

		try
		{
			ByteIlaSwap.create(null, 2);
			fail("ila == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s2 = ByteIlaTest.check(swap2Ila,
			ByteIlaSwap.create(byteIla, 2));		
		assertNull(s2, s2);
		
		String s4 = ByteIlaTest.check(swap4Ila,
			ByteIlaSwap.create(byteIla, 4));
		assertNull(s4, s4);
		
		String s8 = ByteIlaTest.check(swap8Ila,
			ByteIlaSwap.create(byteIla, 8));
		assertNull(s8, s8);
	}
}