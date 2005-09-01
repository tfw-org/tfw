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
import tfw.immutable.ilm.intilm.IntIlm;
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
import tfw.tsm.ecd.ilm.IntIlmECD;
import tfw.visualizer.graph.Graph;
import tfw.visualizer.graph.GraphECD;

public class NodeToGraphicConverter extends Converter
{
	private final Component component;
	private final GraphECD graphECD;
	private final IntIlmECD pixelNodeTLBRECD;
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
	
	public NodeToGraphicConverter(Component component, GraphECD graphECD,
		IntIlmECD pixelNodeTLBRECD, FontECD fontECD,
		ColorECD backgroundColorECD, ColorECD branchColorECD,
		ColorECD commitColorECD, ColorECD converterColorECD,
		ColorECD eventChannelColorECD, ColorECD initiatorColorECD,
		ColorECD multiplexedBranchColorECD, ColorECD rootColorECD,
		ColorECD synchronizerColorECD, ColorECD triggeredCommitColorECD,
		ColorECD triggeredConverterColorECD, ColorECD validatorColorECD,
		GraphicECD graphicOutECD)
	{
		super("NodeEdgeToGraphicConverter",
			new EventChannelDescription[] {graphECD, pixelNodeTLBRECD,
				fontECD, backgroundColorECD, branchColorECD, commitColorECD,
				converterColorECD, eventChannelColorECD, initiatorColorECD,
				multiplexedBranchColorECD, rootColorECD, synchronizerColorECD,
				triggeredCommitColorECD, triggeredConverterColorECD,
				validatorColorECD},
			null,
			new EventChannelDescription[] {graphicOutECD});
		
		this.component = component;
		this.graphECD = graphECD;
		this.pixelNodeTLBRECD = pixelNodeTLBRECD;
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
		Graph graph = (Graph)get(graphECD);
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
		Font font = (Font)get(fontECD);
		FontMetrics fontMetrics = component.getFontMetrics(font);
		int descent = fontMetrics.getDescent();

		int[] tops = null;
		int[] lefts = null;
		int[] bottoms = null;
		int[] rights = null;
		Object[] nodes = new Object[(int)graph.nodesLength()];
		Graphic graphic = null;
		
		try
		{
			int[][] pixelNodeTLBR = ((IntIlm)get(pixelNodeTLBRECD)).toArray();
			tops = pixelNodeTLBR[0];
			lefts = pixelNodeTLBR[1];
			bottoms = pixelNodeTLBR[2];
			rights = pixelNodeTLBR[3];

			graph.toArray(nodes, 0, 0, (int)graph.nodesLength(),
				null, null, 0, 0, 0);
		}
		catch (Exception e)
		{
			return;
		}
		
		String[] strings = new String[nodes.length];

		for (int i=0 ; i < nodes.length ; i++)
		{
			Proxy proxy = (Proxy)nodes[i];
			
			if (proxy == null)
			{
				continue;
			}
			
			Color foregroundColor = Color.BLACK;
			
			strings[i] = ((Proxy)nodes[i]).getName();
			
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
			
			int top = tops[i];
			int left = lefts[i];
			int bottom = bottoms[i];
			int right = rights[i];
			int width = right - left;
			int height = bottom - top;
			
			graphic = SetColorGraphic.create(graphic, backgroundColor);
			graphic = FillRectGraphic.create(graphic, left, top, width, height);
			graphic = SetColorGraphic.create(graphic, foregroundColor);
			graphic = DrawRectGraphic.create(graphic, left, top, width, height);
			graphic = SetFontGraphic.create(graphic, font);
			graphic = DrawStringGraphic.create(
				graphic, proxy.getName(), left, bottom - descent);
		}
				
		if (graphic != null)
		{
			set(graphicOutECD, graphic);
		}
	}
}