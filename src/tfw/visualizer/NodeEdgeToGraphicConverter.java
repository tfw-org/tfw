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

import tfw.awt.ecd.FontECD;
import tfw.awt.ecd.GraphicECD;
import tfw.awt.graphic.DrawStringGraphic;
import tfw.awt.graphic.Graphic;
import tfw.awt.graphic.SetFontGraphic;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.BranchProxy;
import tfw.tsm.CommitProxy;
import tfw.tsm.Converter;
import tfw.tsm.ConverterProxy;
import tfw.tsm.InitiatorProxy;
import tfw.tsm.MultiplexedBranchProxy;
import tfw.tsm.RootProxy;
import tfw.tsm.SynchronizerProxy;
import tfw.tsm.TriggeredCommitProxy;
import tfw.tsm.TriggeredConverterProxy;
import tfw.tsm.ValidatorProxy;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ila.DoubleIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class NodeEdgeToGraphicConverter extends Converter
{
	private final Component component;
	private final ObjectIlaECD nodesECD;
	private final DoubleIlaECD nodeXsECD;
	private final DoubleIlaECD nodeYsECD;
	private final IntegerECD xOffsetECD;
	private final IntegerECD yOffsetECD;
	private final IntegerECD widthECD;
	private final IntegerECD heightECD;
	private final FontECD fontECD;
	private final GraphicECD graphicOutECD;
	
	public NodeEdgeToGraphicConverter(Component component,
		ObjectIlaECD nodesECD, DoubleIlaECD nodeXsECD, DoubleIlaECD nodeYsECD,
		IntegerECD xOffsetECD, IntegerECD yOffsetECD, IntegerECD widthECD,
		IntegerECD heightECD, FontECD fontECD, GraphicECD graphicOutECD)
	{
		super("NodeEdgeToGraphicConverter",
			new EventChannelDescription[] {nodesECD, nodeXsECD, nodeYsECD,
				xOffsetECD, yOffsetECD, widthECD, heightECD, fontECD},
			null,
			new EventChannelDescription[] {graphicOutECD});
		
		this.component = component;
		this.nodesECD = nodesECD;
		this.nodeXsECD = nodeXsECD;
		this.nodeYsECD = nodeYsECD;
		this.xOffsetECD = xOffsetECD;
		this.yOffsetECD = yOffsetECD;
		this.widthECD = widthECD;
		this.heightECD = heightECD;
		this.fontECD = fontECD;
		this.graphicOutECD = graphicOutECD;
	}

	protected void convert()
	{
		Object[] nodes = null;
		double[] nodeXs = null;
		double[] nodeYs = null;
		
		try
		{
			nodes = ((ObjectIla)get(nodesECD)).toArray();
			nodeXs = ((DoubleIla)get(nodeXsECD)).toArray();
			nodeYs = ((DoubleIla)get(nodeYsECD)).toArray();
		}
		catch (DataInvalidException e)
		{
			return;
		}
		
		int xOffset = ((Integer)get(xOffsetECD)).intValue();
		int yOffset = ((Integer)get(yOffsetECD)).intValue();
		int width = ((Integer)get(widthECD)).intValue();
		int height = ((Integer)get(heightECD)).intValue();
		Font font = (Font)get(fontECD);
		FontMetrics fontMetrics = component.getFontMetrics(font);
		int heightOffset = fontMetrics.getHeight() / 2;
		
		String[] strings = new String[nodes.length];
		int[] xs = new int[nodes.length];
		int[] ys = new int[nodes.length];
		
		for (int i=0 ; i < nodes.length ; i++)
		{
			String string = getName(nodes[i]);
			int widthOffset = fontMetrics.stringWidth(string) / 2;
			
			strings[i] = string;
			xs[i] = (int)(nodeXs[i] * (double)width) + xOffset - widthOffset;
			ys[i] = (int)(nodeYs[i] * (double)height) + yOffset - heightOffset;
		}
		
		Graphic setFontGraphic = SetFontGraphic.create(null, font);
		set(graphicOutECD, DrawStringGraphic.create(
			setFontGraphic, strings, xs, ys));
	}
	
	private static String getName(Object proxy)
	{
		if (proxy instanceof RootProxy)
		{
			return((RootProxy)proxy).getName();
		}
		else if (proxy instanceof BranchProxy)
		{
			return((BranchProxy)proxy).getName();
		}
		else if (proxy instanceof CommitProxy)
		{
			return((CommitProxy)proxy).getName();
		}
		else if (proxy instanceof ConverterProxy)
		{
			return((ConverterProxy)proxy).getName();
		}
		else if (proxy instanceof InitiatorProxy)
		{
			return((InitiatorProxy)proxy).getName();
		}
		else if (proxy instanceof MultiplexedBranchProxy)
		{
			return((MultiplexedBranchProxy)proxy).getName();
		}
		else if (proxy instanceof SynchronizerProxy)
		{
			return((SynchronizerProxy)proxy).getName();
		}
		else if (proxy instanceof TriggeredCommitProxy)
		{
			return((TriggeredCommitProxy)proxy).getName();
		}
		else if (proxy instanceof TriggeredConverterProxy)
		{
			return((TriggeredConverterProxy)proxy).getName();
		}
		else if (proxy instanceof ValidatorProxy)
		{
			return((ValidatorProxy)proxy).getName();
		}
		else
		{
			throw new IllegalArgumentException(
				"Expected Proxy ... found "+proxy.getClass());
		}
	}
}