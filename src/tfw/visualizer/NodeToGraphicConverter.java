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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;

import tfw.awt.ecd.ColorECD;
import tfw.awt.ecd.FontECD;
import tfw.awt.ecd.GraphicECD;
import tfw.awt.graphic.DrawRectGraphic;
import tfw.awt.graphic.DrawStringGraphic;
import tfw.awt.graphic.FillRectGraphic;
import tfw.awt.graphic.Graphic;
import tfw.awt.graphic.SetColorGraphic;
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
import tfw.tsm.Proxy;
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
	private final ColorECD backgroundColorECD;
	private final ColorECD branchColorECD;
	private final ColorECD commitColorECD;
	private final ColorECD converterColorECD;
	private final ColorECD eventChannelColorECD;
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
		FontECD fontECD, ColorECD backgroundColorECD, ColorECD branchColorECD,
		ColorECD commitColorECD, ColorECD converterColorECD,
		ColorECD eventChannelColorECD, ColorECD initiatorColorECD,
		ColorECD multiplexedBranchColorECD, ColorECD rootColorECD,
		ColorECD synchronizerColorECD, ColorECD triggeredCommitColorECD,
		ColorECD triggeredConverterColorECD, ColorECD validatorColorECD,
		GraphicECD graphicOutECD)
	{
		super("NodeEdgeToGraphicConverter",
			new EventChannelDescription[] {nodesECD, nodeClusterECD,
				nodeClusterPixelXsECD, nodeClusterPixelYsECD, fontECD,
				backgroundColorECD, branchColorECD, commitColorECD,
				converterColorECD, eventChannelColorECD, initiatorColorECD,
				multiplexedBranchColorECD, rootColorECD, synchronizerColorECD,
				triggeredCommitColorECD, triggeredConverterColorECD,
				validatorColorECD},
			null,
			new EventChannelDescription[] {graphicOutECD});
		
		this.component = component;
		this.nodesECD = nodesECD;
		this.nodeClusterECD = nodeClusterECD;
		this.nodeClusterPixelXsECD = nodeClusterPixelXsECD;
		this.nodeClusterPixelYsECD = nodeClusterPixelYsECD;
		this.fontECD = fontECD;
		this.backgroundColorECD = backgroundColorECD;
		this.branchColorECD = branchColorECD;
		this.commitColorECD = commitColorECD;
		this.converterColorECD = converterColorECD;
		this.eventChannelColorECD = eventChannelColorECD;
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
		Color backgroundColor = (Color)get(backgroundColorECD);
		Color branchColor = (Color)get(branchColorECD);
		Color commitColor = (Color)get(commitColorECD);
		Color converterColor = (Color)get(converterColorECD);
		Color eventChannelColor = (Color)get(eventChannelColorECD);
		Color initiatorColor = (Color)get(initiatorColorECD);
		Color multiplexedBranchColor = (Color)get(multiplexedBranchColorECD);
		Color rootColor = (Color)get(rootColorECD);
		Color synchronizerColor = (Color)get(synchronizerColorECD);
		Color triggeredCommitColor = (Color)get(triggeredCommitColorECD);
		Color triggeredConverterColor = (Color)get(triggeredConverterColorECD);
		Color validatorColor = (Color)get(validatorColorECD);
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
			strings[i] = ((Proxy)nodes[i]).getName();
		}
		
		Font font = (Font)get(fontECD);
		FontMetrics fontMetrics = component.getFontMetrics(font);

		int height = fontMetrics.getHeight();
		int halfHeight = height / 2;
		int descent = fontMetrics.getDescent();
//		int[] stringX = new int[nodes.length];
//		int[] stringY = new int[nodes.length];
//		int[] boxXs = new int[nodes.length];
//		int[] boxYs = new int[nodes.length];
//		int[] boxWidths = new int[nodes.length];
//		int[] boxHeights = new int[nodes.length];
		Graphic graphic = null;
		
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
				Proxy proxy = (Proxy)nodes[node];
				int width = fontMetrics.stringWidth(strings[node]);
				int x = clusterX[j];
				int y = clusterY[j];
				Color foregroundColor = Color.BLACK;
				
				if (proxy instanceof BranchProxy)
					foregroundColor = branchColor;
				else if (proxy instanceof CommitProxy)
					foregroundColor = commitColor;
				else if (proxy instanceof ConverterProxy)
					foregroundColor = converterColor;
				else if (proxy instanceof EventChannelProxy)
					foregroundColor = eventChannelColor;
				else if (proxy instanceof InitiatorProxy)
					foregroundColor = initiatorColor;
				else if (proxy instanceof MultiplexedBranchProxy)
					foregroundColor = multiplexedBranchColor;
				else if (proxy instanceof RootProxy)
					foregroundColor = rootColor;
				else if (proxy instanceof SynchronizerProxy)
					foregroundColor = synchronizerColor;
				else if (proxy instanceof TriggeredCommitProxy)
					foregroundColor = triggeredCommitColor;
				else if (proxy instanceof TriggeredConverterProxy)
					foregroundColor = triggeredConverterColor;
				else if (proxy instanceof ValidatorProxy)
					foregroundColor = validatorColor;
				
				int sx = x - width / 2;
				int sy = y + halfHeight - descent;
				
//				stringX[node] = sx;
//				stringY[node] = sy;
//				boxXs[node] = sx - 5;
//				boxYs[node] = y - halfHeight - 5;
//				boxWidths[node] = width + 10;
//				boxHeights[node] = height +10;
				
				Graphic backgroundColorGraphic = SetColorGraphic.create(
					graphic, backgroundColor);
//				Graphic fillRectGraphic = FillRectGraphic.create(
//					backgroundColorGraphic, boxXs, boxYs, boxWidths, boxHeights);
				Graphic fillRectGraphic = FillRectGraphic.create(
					backgroundColorGraphic, sx-5, y-halfHeight-5, width+10, height+10);
				Graphic foregroundColorGraphic = SetColorGraphic.create(
					fillRectGraphic, foregroundColor);
//				Graphic drawRectGraphic = DrawRectGraphic.create(
//					foregroundColorGraphic, boxXs, boxYs, boxWidths, boxHeights);
				Graphic drawRectGraphic = DrawRectGraphic.create(
					foregroundColorGraphic, sx-5, y-halfHeight-5, width+10, height+10);
				Graphic setFontGraphic = SetFontGraphic.create(drawRectGraphic, font);
//				Graphic drawStringGraphic = DrawStringGraphic.create(
//					setFontGraphic, strings, stringX, stringY);
				Graphic drawStringGraphic = DrawStringGraphic.create(
					setFontGraphic, strings[node], sx, sy);
				
				graphic = drawStringGraphic;
			}
		}
		
		if (graphic != null)
		{
			set(graphicOutECD, graphic);
		}
	}
}