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

import java.awt.BasicStroke;
import java.awt.Color;

import tfw.awt.ecd.GraphicECD;
import tfw.awt.graphic.DrawRectGraphic;
import tfw.awt.graphic.Graphic;
import tfw.awt.graphic.SetColorGraphic;
import tfw.awt.graphic.SetStrokeGraphic;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ilm.intilm.IntIlm;
import tfw.tsm.Converter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.BooleanIlaECD;
import tfw.tsm.ecd.ilm.IntIlmECD;

public class SelectionToGraphicConverter extends Converter
{
	private final BooleanIlaECD selectedNodesECD;
	private final IntIlmECD pixelNodeTLBRECD;
	private final GraphicECD graphicECD;
	
	public SelectionToGraphicConverter(BooleanIlaECD selectedNodesECD,
		IntIlmECD pixelNodeTLBRECD, GraphicECD graphicECD)
	{
		super("SelectionToGraphicConverter",
			new ObjectECD[] {selectedNodesECD, pixelNodeTLBRECD},
			null,
			new ObjectECD[] {graphicECD});
		
		this.selectedNodesECD = selectedNodesECD;
		this.pixelNodeTLBRECD = pixelNodeTLBRECD;
		this.graphicECD = graphicECD;
	}
	
	protected void convert()
	{
		boolean[] selectedNodes = null;
		int[][] tlbr = null;
		int width = -1;
		Graphic graphic = SetStrokeGraphic.create(
			SetColorGraphic.create(null, Color.red), new BasicStroke(3.0f));
		
		try
		{
			selectedNodes = ((BooleanIla)get(selectedNodesECD)).toArray();
			
			IntIlm intIlm = (IntIlm)get(pixelNodeTLBRECD);
			tlbr = intIlm.toArray();
			width = (int)intIlm.width();
		}
		catch (DataInvalidException die)
		{
			return;
		}
		
		int[] tops = tlbr[0];
		int[] lefts = tlbr[1];
		int[] bottoms = tlbr[2];
		int[] rights = tlbr[3];
		
		for (int i=0 ; i < width ; i++)
		{
			if (i < selectedNodes.length && selectedNodes[i])
			{
				graphic = DrawRectGraphic.create(graphic, lefts[i]-1, tops[i]-1,
					rights[i]-lefts[i]+2, bottoms[i]-tops[i]+2);
			}
		}
		
		set(graphicECD, graphic);
	}
}