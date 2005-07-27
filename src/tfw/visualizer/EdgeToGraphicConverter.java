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

import java.util.ArrayList;
import java.util.HashMap;

import tfw.awt.ecd.GraphicECD;
import tfw.awt.graphic.DrawLineGraphic;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.Converter;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class EdgeToGraphicConverter extends Converter
{
	private final ObjectIlaECD nodeClusterECD;
	private final ObjectIlaECD nodeClusterFromsECD;
	private final ObjectIlaECD nodeClusterTosECD;
	private final ObjectIlaECD nodeClusterPixelXsECD;
	private final ObjectIlaECD nodeClusterPixelYsECD;
	private final GraphicECD graphicECD;
	
	public EdgeToGraphicConverter(
			ObjectIlaECD nodeClusterECD, ObjectIlaECD nodeClusterFromsECD,
			ObjectIlaECD nodeClusterTosECD, ObjectIlaECD nodeClusterPixelXsECD,
			ObjectIlaECD nodeClusterPixelYsECD, GraphicECD graphicECD)
	{
		super("EdgeToGraphicConverter",
			new EventChannelDescription[] {nodeClusterECD, nodeClusterFromsECD,
				nodeClusterTosECD, nodeClusterPixelXsECD, nodeClusterPixelYsECD},
			null,
			new EventChannelDescription[] {graphicECD});
		
		this.nodeClusterECD = nodeClusterECD;
		this.nodeClusterFromsECD = nodeClusterFromsECD;
		this.nodeClusterTosECD = nodeClusterTosECD;
		this.nodeClusterPixelXsECD = nodeClusterPixelXsECD;
		this.nodeClusterPixelYsECD = nodeClusterPixelYsECD;
		this.graphicECD = graphicECD;
	}
	
	protected void convert()
	{
		Object[] nodeCluster = null;
		Object[] nodeClusterFroms = null;
		Object[] nodeClusterTos = null;
		Object[] nodeClusterPixelXs = null;
		Object[] nodeClusterPixelYs = null;

		try
		{
			nodeCluster = ((ObjectIla)get(nodeClusterECD)).toArray();
			nodeClusterFroms = ((ObjectIla)get(nodeClusterFromsECD)).toArray();
			nodeClusterTos = ((ObjectIla)get(nodeClusterTosECD)).toArray();
			nodeClusterPixelXs = ((ObjectIla)get(nodeClusterPixelXsECD)).toArray();
			nodeClusterPixelYs = ((ObjectIla)get(nodeClusterPixelYsECD)).toArray();
		}
		catch (DataInvalidException e)
		{
			return;
		}
		
		ArrayList pixelFromX = new ArrayList();
		ArrayList pixelFromY = new ArrayList();
		ArrayList pixelToX = new ArrayList();
		ArrayList pixelToY = new ArrayList();
		
		for (int i=0 ; i < nodeCluster.length ; i++)
		{
			long[] nodes = null;
			long[] froms = null;
			long[] tos = null;
			int[] pixelXs = null;
			int[] pixelYs = null;
			HashMap xMap = new HashMap();
			HashMap yMap = new HashMap();
			
			try
			{
				nodes = ((LongIla)nodeCluster[i]).toArray();
				froms = ((LongIla)nodeClusterFroms[i]).toArray();
				tos = ((LongIla)nodeClusterTos[i]).toArray();
				pixelXs = ((IntIla)nodeClusterPixelXs[i]).toArray();
				pixelYs = ((IntIla)nodeClusterPixelYs[i]).toArray();
			}
			catch (DataInvalidException e)
			{
				return;
			}
			
			for (int j=0 ; j < nodes.length ; j++)
			{
				xMap.put(new Long(nodes[j]), new Integer(pixelXs[j]));
				yMap.put(new Long(nodes[j]), new Integer(pixelYs[j]));
			}
			
			for (int j=0 ; j < froms.length ; j++)
			{
				Long from = new Long(froms[j]);
				Long to = new Long(tos[j]);
				
				pixelFromX.add(xMap.get(from));
				pixelFromY.add(yMap.get(from));
				pixelToX.add(xMap.get(to));
				pixelToY.add(yMap.get(to));
			}
		}
		
		int[] x1s = new int[pixelFromX.size()];
		int[] y1s = new int[pixelFromY.size()];
		int[] x2s = new int[pixelToX.size()];
		int[] y2s = new int[pixelToY.size()];
		
		for (int i=0 ; i < x1s.length ; i++)
		{
			x1s[i] = ((Integer)pixelFromX.get(i)).intValue();
			y1s[i] = ((Integer)pixelFromY.get(i)).intValue();
			x2s[i] = ((Integer)pixelToX.get(i)).intValue();
			y2s[i] = ((Integer)pixelToY.get(i)).intValue();
		}
		
		set(graphicECD, DrawLineGraphic.create(null, x1s, y1s, x2s, y2s));
	}
}