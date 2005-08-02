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

import tfw.immutable.ila.intila.IntIlaFill;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.Converter;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.ila.IntIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class ClusterRectangleConverter extends Converter
{
	private final IntegerECD xECD;
	private final IntegerECD yECD;
	private final IntegerECD widthECD;
	private final IntegerECD heightECD;
	private final ObjectIlaECD nodeClustersECD;
	private final IntIlaECD clusterXsECD;
	private final IntIlaECD clusterYsECD;
	private final IntIlaECD clusterWidthsECD;
	private final IntIlaECD clusterHeightsECD;
	
	public ClusterRectangleConverter(StatelessTriggerECD triggerECD,
		IntegerECD xECD, IntegerECD yECD, IntegerECD widthECD,
		IntegerECD heightECD, ObjectIlaECD nodeClustersECD,
		IntIlaECD clusterXsECD, IntIlaECD clusterYsECD,
		IntIlaECD clusterWidthsECD, IntIlaECD clusterHeightsECD)
	{
		super("ClusterRectangleConverter",
			new EventChannelDescription[] {xECD, yECD, widthECD, heightECD,
				nodeClustersECD},
			null,
			new EventChannelDescription[] {clusterXsECD, clusterYsECD,
				clusterWidthsECD, clusterHeightsECD});
		
		this.xECD = xECD;
		this.yECD = yECD;
		this.widthECD = widthECD;
		this.heightECD = heightECD;
		this.nodeClustersECD = nodeClustersECD;
		this.clusterXsECD = clusterXsECD;
		this.clusterYsECD = clusterYsECD;
		this.clusterWidthsECD = clusterWidthsECD;
		this.clusterHeightsECD = clusterHeightsECD;
	}
	
	protected void convert()
	{
		int x = ((Integer)get(xECD)).intValue();
		int y = ((Integer)get(yECD)).intValue();
		int width = ((Integer)get(widthECD)).intValue();
		int height = ((Integer)get(heightECD)).intValue();
		int numberOfClusters = (int)((ObjectIla)get(nodeClustersECD)).length();
		int dimension = (int)Math.ceil(Math.sqrt(numberOfClusters));
		int clusterWidth = width / dimension;
		int clusterHeight = height / dimension;
		int[] xs = new int[numberOfClusters];
		int[] ys = new int[numberOfClusters];

		for (int c=0 ; c < numberOfClusters ; c++)
		{
			xs[c] = x + c % dimension * clusterWidth;
			ys[c] = y + c / dimension * clusterHeight;
		}
		
		set(clusterXsECD, IntIlaFromArray.create(xs));
		set(clusterYsECD, IntIlaFromArray.create(ys));
		set(clusterWidthsECD, IntIlaFill.create(
			clusterWidth, numberOfClusters));
		set(clusterHeightsECD, IntIlaFill.create(
			clusterHeight, numberOfClusters));
	}
}