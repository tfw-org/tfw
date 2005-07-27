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

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;

import tfw.awt.ecd.ColorECD;
import tfw.awt.ecd.FontECD;
import tfw.awt.ecd.GraphicECD;
import tfw.awt.graphic.DrawStringGraphic;
import tfw.awt.graphic.Graphic;
import tfw.awt.graphic.SetFontGraphic;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.BranchProxy;
import tfw.tsm.CommitProxy;
import tfw.tsm.Converter;
import tfw.tsm.ConverterProxy;
import tfw.tsm.EventChannelProxy;
import tfw.tsm.InitiatorProxy;
import tfw.tsm.MultiplexedBranchProxy;
import tfw.tsm.RootProxy;
import tfw.tsm.SynchronizerProxy;
import tfw.tsm.TriggeredCommitProxy;
import tfw.tsm.TriggeredConverterProxy;
import tfw.tsm.ValidatorProxy;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class NodeToGraphicConverter extends Converter
{
	private final Component component;
	private final ObjectIlaECD nodesECD;
	private final ObjectIlaECD nodeClusterECD;
	private final ObjectIlaECD nodeClusterPixelXsECD;
	private final ObjectIlaECD nodeClusterPixelYsECD;
	private final FontECD fontECD;
	private final ColorECD branchColorECD;
	private final ColorECD commitColorECD;
	private final ColorECD converterColorECD;
	private final ColorECD initiatorColorECD;
	private final ColorECD multiplexedBranchColorECD;
	private final ColorECD rootColorECD;
	private final ColorECD synchronizerColorECD;
	private final ColorECD triggeredCommitColorECD;
	private final ColorECD triggeredConverterColorECD;
	private final ColorECD validatorColorECD;
	private final GraphicECD graphicOutECD;
	
	public NodeToGraphicConverter(Component component,
		ObjectIlaECD nodesECD, ObjectIlaECD nodeClusterECD,
		ObjectIlaECD nodeClusterPixelXsECD, ObjectIlaECD nodeClusterPixelYsECD,
		FontECD fontECD, ColorECD branchColorECD,
		ColorECD commitColorECD, ColorECD converterColorECD,
		ColorECD initiatorColorECD, ColorECD multiplexedBranchColorECD,
		ColorECD rootColorECD, ColorECD synchronizerColorECD, 
		ColorECD triggeredCommitColorECD, ColorECD triggeredConverterColorECD,
		ColorECD validatorColorECD, GraphicECD graphicOutECD)
	{
		super("NodeEdgeToGraphicConverter",
			new EventChannelDescription[] {nodesECD, nodeClusterECD,
				nodeClusterPixelXsECD, nodeClusterPixelYsECD, fontECD,
				branchColorECD, commitColorECD, converterColorECD,
				initiatorColorECD, multiplexedBranchColorECD, rootColorECD,
				synchronizerColorECD, triggeredCommitColorECD,
				triggeredConverterColorECD, validatorColorECD},
			null,
			new EventChannelDescription[] {graphicOutECD});
		
		this.component = component;
		this.nodesECD = nodesECD;
		this.nodeClusterECD = nodeClusterECD;
		this.nodeClusterPixelXsECD = nodeClusterPixelXsECD;
		this.nodeClusterPixelYsECD = nodeClusterPixelYsECD;
		this.fontECD = fontECD;
		this.branchColorECD = branchColorECD;
		this.commitColorECD = commitColorECD;
		this.converterColorECD = converterColorECD;
		this.initiatorColorECD = initiatorColorECD;
		this.multiplexedBranchColorECD = multiplexedBranchColorECD;
		this.rootColorECD = rootColorECD;
		this.synchronizerColorECD = synchronizerColorECD;
		this.triggeredCommitColorECD = triggeredCommitColorECD;
		this.triggeredConverterColorECD = triggeredConverterColorECD;
		this.validatorColorECD = validatorColorECD;
		this.graphicOutECD = graphicOutECD;
	}

	protected void convert()
	{
		Object[] nodes = null;
		Object[] nodeClusters = null;
		Object[] nodeClusterPixelXs = null;
		Object[] nodeClusterPixelYs = null;
		
		try
		{
			nodes = ((ObjectIla)get(nodesECD)).toArray();
			nodeClusters = ((ObjectIla)get(nodeClusterECD)).toArray();
			nodeClusterPixelXs = ((ObjectIla)get(nodeClusterPixelXsECD)).toArray();
			nodeClusterPixelYs = ((ObjectIla)get(nodeClusterPixelYsECD)).toArray();
		}
		catch (DataInvalidException e)
		{
			return;
		}
		
		String[] strings = new String[nodes.length];
		
		for (int i=0 ; i < nodes.length ; i++)
		{
			Object proxy = nodes[i];
			
			if (proxy instanceof RootProxy)
			{
				strings[i] = ((RootProxy)proxy).getName();
			}
			else if (proxy instanceof BranchProxy)
			{
				strings[i] = ((BranchProxy)proxy).getName();
			}
			else if (proxy instanceof CommitProxy)
			{
				strings[i] = ((CommitProxy)proxy).getName();
			}
			else if (proxy instanceof ConverterProxy)
			{
				strings[i] = ((ConverterProxy)proxy).getName();
			}
			else if (proxy instanceof EventChannelProxy)
			{
				strings[i] = ((EventChannelProxy)proxy)
					.getEventChannelDescription().getEventChannelName();
			}
			else if (proxy instanceof InitiatorProxy)
			{
				strings[i] = ((InitiatorProxy)proxy).getName();
			}
			else if (proxy instanceof MultiplexedBranchProxy)
			{
				strings[i] = ((MultiplexedBranchProxy)proxy).getName();
			}
			else if (proxy instanceof SynchronizerProxy)
			{
				strings[i] = ((SynchronizerProxy)proxy).getName();
			}
			else if (proxy instanceof TriggeredCommitProxy)
			{
				strings[i] = ((TriggeredCommitProxy)proxy).getName();
			}
			else if (proxy instanceof TriggeredConverterProxy)
			{
				strings[i] = ((TriggeredConverterProxy)proxy).getName();
			}
			else if (proxy instanceof ValidatorProxy)
			{
				strings[i] = ((ValidatorProxy)proxy).getName();
			}
			else
			{
				throw new IllegalArgumentException(
					"Expected Proxy ... found "+proxy.getClass());
			}
		}
		
		Font font = (Font)get(fontECD);
		FontMetrics fontMetrics = component.getFontMetrics(font);
		int heightOffset = fontMetrics.getHeight() / 2;
		int[] xs = new int[nodes.length];
		int[] ys = new int[nodes.length];
		
		for (int i=0 ; i < nodeClusters.length ; i++)
		{
			long[] cluster = null;
			int[] clusterX = null;
			int[] clusterY = null;
			
			try
			{
				cluster = ((LongIla)nodeClusters[i]).toArray();
				clusterX = ((IntIla)nodeClusterPixelXs[i]).toArray();
				clusterY = ((IntIla)nodeClusterPixelYs[i]).toArray();
			}
			catch (DataInvalidException e)
			{
				return;
			}
			
			for (int j=0 ; j < cluster.length ; j++)
			{
				int node = (int)cluster[j];
				
				xs[node] = clusterX[j] - fontMetrics.stringWidth(strings[node]) / 2;
				ys[node] = clusterY[j] + heightOffset;
			}
		}
		
		Graphic setFontGraphic = SetFontGraphic.create(null, font);
		set(graphicOutECD, DrawStringGraphic.create(
			setFontGraphic, strings, xs, ys));
	}
}