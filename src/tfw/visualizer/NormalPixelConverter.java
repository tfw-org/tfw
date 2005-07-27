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
package tfw.visualizer;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.Converter;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ila.IntIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class NormalPixelConverter extends Converter
{
	private final IntIlaECD clusterXsECD;
	private final IntIlaECD clusterYsECD;
	private final IntIlaECD clusterWidthsECD;
	private final IntIlaECD clusterHeightsECD;
	private final ObjectIlaECD nodeClusterNormalXsECD;
	private final ObjectIlaECD nodeClusterNormalYsECD;
	private final ObjectIlaECD nodeClusterPixelXsECD;
	private final ObjectIlaECD nodeClusterPixelYsECD;
	
	public NormalPixelConverter(IntIlaECD clusterXsECD, IntIlaECD clusterYsECD,
		IntIlaECD clusterWidthsECD, IntIlaECD clusterHeightsECD,
		ObjectIlaECD nodeClusterNormalXsECD, ObjectIlaECD nodeClusterNormalYsECD,
		ObjectIlaECD nodeClusterPixelXsECD, ObjectIlaECD nodeClusterPixelYsECD)
	{
		super("NormalPixelConverter",
			new EventChannelDescription[] {clusterXsECD, clusterYsECD,
				clusterWidthsECD, clusterHeightsECD, nodeClusterNormalXsECD,
				nodeClusterNormalYsECD},
			null,
			new EventChannelDescription[] {nodeClusterPixelXsECD,
				nodeClusterPixelYsECD});
		
		this.clusterXsECD = clusterXsECD;
		this.clusterYsECD = clusterYsECD;
		this.clusterWidthsECD = clusterWidthsECD;
		this.clusterHeightsECD = clusterHeightsECD;
		this.nodeClusterNormalXsECD = nodeClusterNormalXsECD;
		this.nodeClusterNormalYsECD = nodeClusterNormalYsECD;
		this.nodeClusterPixelXsECD = nodeClusterPixelXsECD;
		this.nodeClusterPixelYsECD = nodeClusterPixelYsECD;
	}
	
	protected void convert()
	{
		int[] clusterXs = null;
		int[] clusterYs = null;
		int[] clusterWidths = null;
		int[] clusterHeights = null;
		Object[] nodeClusterNormalXs = null;
		Object[] nodeClusterNormalYs = null;
		
		try
		{
			clusterXs = ((IntIla)get(clusterXsECD)).toArray();
			clusterYs = ((IntIla)get(clusterYsECD)).toArray();
			clusterWidths = ((IntIla)get(clusterWidthsECD)).toArray();
			clusterHeights = ((IntIla)get(clusterHeightsECD)).toArray();
			nodeClusterNormalXs = ((ObjectIla)get(nodeClusterNormalXsECD)).toArray();
			nodeClusterNormalYs = ((ObjectIla)get(nodeClusterNormalYsECD)).toArray();
		}
		catch (DataInvalidException e)
		{
			return;
		}
		
		Object[] nodeClusterPixelXs = new Object[nodeClusterNormalXs.length];
		Object[] nodeClusterPixelYs = new Object[nodeClusterNormalYs.length];
		
		for (int i=0 ; i < clusterXs.length ; i++)
		{
			int x = clusterXs[i];
			int y = clusterYs[i];
			int width = clusterWidths[i];
			int height = clusterHeights[i];
			double[] normalXs = null;
			double[] normalYs = null;
			
			try
			{
				normalXs = ((DoubleIla)nodeClusterNormalXs[i]).toArray();
				normalYs = ((DoubleIla)nodeClusterNormalYs[i]).toArray();
			}
			catch (DataInvalidException e)
			{
				return;
			}
			
			int[] pixelXs = new int[normalXs.length];
			int[] pixelYs = new int[normalYs.length];
			
			for (int j=0 ; j < normalXs.length ; j++)
			{
				pixelXs[j] = x + (int)(normalXs[j] * (double)width);
				pixelYs[j] = y + (int)(normalYs[j] * (double)height);
			}
			
			nodeClusterPixelXs[i] = IntIlaFromArray.create(pixelXs);
			nodeClusterPixelYs[i] = IntIlaFromArray.create(pixelYs);
		}
		
		set(nodeClusterPixelXsECD, ObjectIlaFromArray.create(nodeClusterPixelXs));
		set(nodeClusterPixelYsECD, ObjectIlaFromArray.create(nodeClusterPixelYs));
	}
}