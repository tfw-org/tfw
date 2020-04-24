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
import tfw.audio.au.Au;
import tfw.audio.au.AuAudioDataFromNormalizedDoubleIla;
import tfw.audio.au.NormalizedDoubleIlaFromAuAudioData;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.doubleila.DoubleIla;

public class AuAudioDataTest extends TestCase
{
	public void testMuLawAuAudioData() throws Exception
	{
		byte[] origByteArray = new byte[256];
		byte[] finalByteArray = new byte[256];
		
		for (int i=0 ; i < origByteArray.length ; i++)
		{
			origByteArray[i] = (byte)i;
			finalByteArray[i] = (byte)i;
		}
		finalByteArray[127] = (byte)255; // There are two zeros.
		
		ByteIla origByteIla = ByteIlaFromArray.create(origByteArray);
		
		DoubleIla doubleIla = NormalizedDoubleIlaFromAuAudioData.create(
			origByteIla, Au.SUN_MAGIC_NUMBER, Au.ISDN_U_LAW_8_BIT);
		ByteIla byteIla = AuAudioDataFromNormalizedDoubleIla.create(
			doubleIla, Au.ISDN_U_LAW_8_BIT);
		
		byte[] b = byteIla.toArray();
		
		assertTrue(Arrays.equals(finalByteArray, b));
	}

	public void test8BitLinearAuAudioData() throws Exception
	{
		byte[] origByteArray = new byte[256];
		byte[] finalByteArray = new byte[256];
		
		for (int i=0 ; i < origByteArray.length ; i++)
		{
			origByteArray[i] = (byte)i;
			finalByteArray[i] = (byte)i;
		}
		finalByteArray[128] = (byte)129; // -Byte.MAX_VALUE-1 = -Byte.MAX_VALUE
		
		ByteIla origByteIla = ByteIlaFromArray.create(origByteArray);
		
		DoubleIla doubleIla = NormalizedDoubleIlaFromAuAudioData.create(
			origByteIla, Au.SUN_MAGIC_NUMBER, Au.LINEAR_8_BIT);
		
		ByteIla byteIla = AuAudioDataFromNormalizedDoubleIla.create(
			doubleIla, Au.LINEAR_8_BIT);
		
		byte[] b = byteIla.toArray();
		
		assertTrue(Arrays.equals(finalByteArray, b));
	}

	public void testALawAuAudioData() throws Exception
	{
		byte[] origByteArray = new byte[256];
		
		for (int i=0 ; i < origByteArray.length ; i++)
		{
			origByteArray[i] = (byte)i;
		}
		
		ByteIla origByteIla = ByteIlaFromArray.create(origByteArray);
		
		DoubleIla doubleIla = NormalizedDoubleIlaFromAuAudioData.create(
			origByteIla, Au.SUN_MAGIC_NUMBER, Au.ISDN_A_LAW_8_BIT);
		ByteIla byteIla = AuAudioDataFromNormalizedDoubleIla.create(
			doubleIla, Au.ISDN_A_LAW_8_BIT);
		
		byte[] b = byteIla.toArray();
		
		assertTrue(Arrays.equals(origByteArray, b));
	}
}