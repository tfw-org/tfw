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

package tfw.immutable.ila.byteila.test;

import junit.framework.TestCase;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.byteila.ByteIlaFromIntIla;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.test.IlaTestDimensions;

public class ByteIlaFromIntIlaTest extends TestCase
{
	public void testByteIlaFromIntIla() throws Exception
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
		int[] intArray = new int[] {
			0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f,
			0x00102030, 0x40506070, 0x8090a0b0, 0xc0d0e0f0,
			0x00112233, 0x44556677, 0x8899aabb, 0xccddeeff};
				
		ByteIla targetIla = ByteIlaFromArray.create(byteArray);
		IntIla intIla = IntIlaFromArray.create(intArray);

		try
		{
			ByteIlaFromIntIla.create(null);
			fail("ilm == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		ByteIla actualIla = ByteIlaFromIntIla.create(intIla);
        final byte epsilon = (byte)0;
        ByteIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
	}
}
