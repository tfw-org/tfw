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
package tfw.build;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public final class SearchAndReplace
{
	private SearchAndReplace() {}
	
	public static final void main(String[] args) throws IOException
	{
		if (args.length < 3 || args.length % 2 == 0)
		{
			System.err.println("Usage: SearchAndReplace <mapping file> "+
				"<input files> <output files>");
			System.exit(-1);
		}
		
		int offset = (args.length - 1) / 2;

System.out.println("mapping = "+args[0]);

		File mFile = new File(args[0]);
		FileInputStream mfr = new FileInputStream(mFile);
		Properties p = new Properties();
		p.load(mfr);

		for (int i=1 ; i <= offset ; i++)
		{
System.out.println("input = "+args[i]);

			File iFile = new File(args[i]);
			FileInputStream ifr = new FileInputStream(iFile);
			byte[] b = new byte[(int)iFile.length()];
			ifr.read(b);
			ifr.close();
			String s = new String(b);

			for (Enumeration e = p.propertyNames() ; e.hasMoreElements() ; )
			{
				String k = (String)e.nextElement();

				s = s.replaceAll(k, p.getProperty(k));
			}

System.out.println("output = "+args[i+offset]);

			File oFile = new File(args[i+offset]);
			FileOutputStream ofos = new FileOutputStream(oFile);
			ofos.write(s.getBytes());
			ofos.close();
		}
	}
}