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
package tfw.audio.demo;

import java.io.File;
import java.io.IOException;
import tfw.audio.au.Au;
import tfw.audio.au.NormalizedDoubleIlaFromAuAudioData;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromFile;
import tfw.immutable.ila.doubleila.DoubleIla;

public class AuDemo
{
	
	public static void main(String[] args)
		throws DataInvalidException, IOException
	{
		File file = new File(args[0]);
		ByteIla byteIla = ByteIlaFromFile.create(file);
		Au auFileFormat = new Au(byteIla);
		
		System.out.println("filename = "+args[0]);
		System.out.println("file.length = "+file.length());
		System.out.println("magicNumber="+Long.toHexString(auFileFormat.magicNumber));
		System.out.println("offset = "+auFileFormat.offset);
		System.out.println("data_size = "+auFileFormat.data_size);
		System.out.println("encoding = "+auFileFormat.encoding);
		System.out.println("sampleRate = "+auFileFormat.sampleRate);
		System.out.println("numberOfChannels = "+auFileFormat.numberOfChannels);
		System.out.println("annotation = "+new String(auFileFormat.annotation.toArray()));
		System.out.println("data.length = "+auFileFormat.audioData.length());
		
		DoubleIla normalizedData = NormalizedDoubleIlaFromAuAudioData.create(
			auFileFormat.audioData, auFileFormat.magicNumber, auFileFormat.encoding);
		
		System.out.println("normalizedData = "+normalizedData);

		if (normalizedData != null)
		{
			double[] d = normalizedData.toArray(0, 10);
			for (int i=0 ; i < d.length ; i++)
			{
				System.out.println("  nD["+i+"]="+d[i]);
			}
		}
	}
}
