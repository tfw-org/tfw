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
package tfw.audio.au;

import tfw.audio.byteila.ALawByteIlaFromLinearShortIla;
import tfw.audio.byteila.MuLawByteIlaFromLinearShortIla;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromCastDoubleIla;
import tfw.immutable.ila.byteila.ByteIlaFromIntIla;
import tfw.immutable.ila.byteila.ByteIlaFromLongIla;
import tfw.immutable.ila.byteila.ByteIlaFromShortIla;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaClip;
import tfw.immutable.ila.doubleila.DoubleIlaRound;
import tfw.immutable.ila.doubleila.DoubleIlaScalarMultiply;
import tfw.immutable.ila.floatila.FloatIlaFromCastDoubleIla;
import tfw.immutable.ila.intila.IntIlaFromCastDoubleIla;
import tfw.immutable.ila.intila.IntIlaFromFloatIla;
import tfw.immutable.ila.longila.LongIlaFromDoubleIla;
import tfw.immutable.ila.shortila.ShortIlaFromCastDoubleIla;

public class AuAudioDataFromNormalizedDoubleIla
{
	private AuAudioDataFromNormalizedDoubleIla() {}
	
	public static ByteIla create(DoubleIla doubleIla, long auEncoding)
	{
		Argument.assertNotNull(doubleIla, "doubleIla");
		
		if (auEncoding == Au.ISDN_U_LAW_8_BIT)
		{
			return(MuLawByteIlaFromLinearShortIla.create(
				ShortIlaFromCastDoubleIla.create(
				DoubleIlaRound.create(
				DoubleIlaScalarMultiply.create(
				DoubleIlaClip.create(doubleIla, -1.0, 1.0),
				Math.pow(2.0, 15.0) - 1.0)))));
		}
		else if (auEncoding == Au.LINEAR_8_BIT)
		{
			return(ByteIlaFromCastDoubleIla.create(
				DoubleIlaRound.create(
				DoubleIlaScalarMultiply.create(
				DoubleIlaClip.create(doubleIla, -1.0, 1.0),
				Math.pow(2.0, 7.0) - 1.0))));
		}
		else if (auEncoding == Au.LINEAR_16_BIT)
		{
			return(ByteIlaFromShortIla.create(
				ShortIlaFromCastDoubleIla.create(
				DoubleIlaRound.create(
				DoubleIlaScalarMultiply.create(
				DoubleIlaClip.create(doubleIla, -1.0, 1.0),
				Math.pow(2.0, 15.0) - 1.0)))));
		}
		else if (auEncoding == Au.LINEAR_32_BIT)
		{
			return(ByteIlaFromIntIla.create(
				IntIlaFromCastDoubleIla.create(
				DoubleIlaRound.create(
				DoubleIlaScalarMultiply.create(
				DoubleIlaClip.create(doubleIla, -1.0, 1.0),
				Math.pow(2.0, 31.0) - 1.0)))));
		}
		else if (auEncoding == Au.IEEE_FLOATING_POINT_32_BIT)
		{
			return(ByteIlaFromIntIla.create(
				IntIlaFromFloatIla.create(
				FloatIlaFromCastDoubleIla.create(
				DoubleIlaClip.create(doubleIla, -1.0, 1.0)))));
		}
		else if (auEncoding == Au.IEEE_FLOATING_POINT_64_BIT)
		{
			return(ByteIlaFromLongIla.create(
				LongIlaFromDoubleIla.create(
				DoubleIlaClip.create(doubleIla, -1.0, 1.0))));
		}
		else if (auEncoding == Au.ISDN_A_LAW_8_BIT)
		{
			return(ALawByteIlaFromLinearShortIla.create(
				ShortIlaFromCastDoubleIla.create(
				DoubleIlaRound.create(
				DoubleIlaScalarMultiply.create(
				DoubleIlaClip.create(doubleIla, -1.0, 1.0),
				Math.pow(2.0, 15.0) - 1.0)))));
		}
		
		throw new IllegalArgumentException(
			"Unsupported Au encoding type: "+auEncoding);
	}
}