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

import tfw.awt.ecd.FontECD;
import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.immutable.ilm.intilm.IntIlm;
import tfw.tsm.Converter;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ilm.DoubleIlmECD;
import tfw.tsm.ecd.ilm.IntIlmECD;
import tfw.visualizer.graph.Graph;
import tfw.visualizer.graph.GraphECD;

public class NormalPixelConverter extends Converter
{
	private final Component component;
	private final GraphECD graphECD;
	private final DoubleIlmECD normalizedXYsECD;
	private final IntegerECD graphXOffsetECD;
	private final IntegerECD graphYOffsetECD;
	private final IntegerECD graphWidthECD;
	private final IntegerECD graphHeightECD;
	private final FontECD fontECD;
	private final IntIlmECD pixelNodeTLBRECD;
	
	public NormalPixelConverter(Component component, GraphECD graphECD,
		DoubleIlmECD normalizedXYsECD, IntegerECD graphXOffsetECD,
		IntegerECD graphYOffsetECD, IntegerECD graphWidthECD,
		IntegerECD graphHeightECD, FontECD fontECD,
		IntIlmECD pixelNodeTLBRECD)
	{
		super("NormalPixelConverter",
			new EventChannelDescription[] {graphECD, normalizedXYsECD,
				graphXOffsetECD, graphYOffsetECD, graphWidthECD, graphHeightECD,
				fontECD},
			null,
			new EventChannelDescription[] {pixelNodeTLBRECD});

		this.component = component;
		this.graphECD = graphECD;
		this.normalizedXYsECD = normalizedXYsECD;
		this.graphXOffsetECD = graphXOffsetECD;
		this.graphYOffsetECD = graphYOffsetECD;
		this.graphWidthECD = graphWidthECD;
		this.graphHeightECD = graphHeightECD;
		this.fontECD = fontECD;
		this.pixelNodeTLBRECD = pixelNodeTLBRECD;
	}
	
	protected void convert()
	{
		Graph graph = (Graph)get(graphECD);
		DoubleIlm normalizedXYs = (DoubleIlm)get(normalizedXYsECD);
		int graphXOffset = ((Integer)get(graphXOffsetECD)).intValue();
		int graphYOffset = ((Integer)get(graphYOffsetECD)).intValue();
		int graphWidth = ((Integer)get(graphWidthECD)).intValue();
		int graphHeight = ((Integer)get(graphHeightECD)).intValue();
		Font font = (Font)get(fontECD);

		IntIlm pixelNodeBoundsFromNormalizedXYs =
			PixelNodeBoundsFromNormalizedXYs.create(graph, normalizedXYs,
			graphXOffset, graphYOffset, graphWidth, graphHeight,
			component.getFontMetrics(font));

		set(pixelNodeTLBRECD, pixelNodeBoundsFromNormalizedXYs);
	}
}