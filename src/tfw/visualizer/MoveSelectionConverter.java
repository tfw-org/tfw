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
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ilm.intilm.IntIlm;
import tfw.immutable.ilm.intilm.IntIlmFromArray;
import tfw.tsm.Converter;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ila.BooleanIlaECD;
import tfw.tsm.ecd.ilm.IntIlmECD;

public class MoveSelectionConverter extends Converter
{
	private final IntegerECD xMouseECD;
	private final IntegerECD yMouseECD;
	private final BooleanECD selectedECD;
	private final IntIlmECD pixelNodeTLBRECD;
	private final BooleanIlaECD selectedNodesECD;
	private final BooleanECD buttonOnePressedECD;
	private final BooleanECD buttonTwoPressedECD;
	private final BooleanECD buttonThreePressedECD;
	
	public MoveSelectionConverter(IntegerECD xMouseECD, IntegerECD yMouseECD,
		BooleanECD selectedECD, BooleanIlaECD selectedNodesECD,
		IntIlmECD pixelNodeTLBRECD, BooleanECD buttonOnePressedECD,
		BooleanECD buttonTwoPressedECD, BooleanECD buttonThreePressedECD)
	{
		super("MoveSelectionConverter",
			new EventChannelDescription[] {xMouseECD, yMouseECD, selectedECD},
			new EventChannelDescription[] {selectedNodesECD, pixelNodeTLBRECD,
				buttonOnePressedECD, buttonTwoPressedECD,
				buttonThreePressedECD},
			new EventChannelDescription[] {pixelNodeTLBRECD});
		
		this.xMouseECD = xMouseECD;
		this.yMouseECD = yMouseECD;
		this.selectedECD = selectedECD;
		this.selectedNodesECD = selectedNodesECD;
		this.pixelNodeTLBRECD = pixelNodeTLBRECD;
		this.buttonOnePressedECD = buttonOnePressedECD;
		this.buttonTwoPressedECD = buttonTwoPressedECD;
		this.buttonThreePressedECD = buttonThreePressedECD;
	}
	
	protected void convert()
	{
		if (((Boolean)get(selectedECD)).booleanValue() &&
			((Boolean)get(buttonOnePressedECD)).booleanValue() &&
			((Boolean)getPreviousTransactionState(buttonOnePressedECD)).booleanValue() &&
			!((Boolean)get(buttonTwoPressedECD)).booleanValue() &&
			!((Boolean)get(buttonThreePressedECD)).booleanValue())
		{
			boolean[] selectedNodes = null;
			int[][] tlbr = null;
			int[] tops = null;
			int[] lefts = null;
			int[] bottoms = null;
			int[] rights = null;
			
			try
			{
				selectedNodes = ((BooleanIla)get(selectedNodesECD)).toArray();
				tlbr = ((IntIlm)get(pixelNodeTLBRECD)).toArray();
				
				tops = tlbr[0];
				lefts = tlbr[1];
				bottoms = tlbr[2];
				rights = tlbr[3];
			}
			catch (DataInvalidException die)
			{
				return;
			}
			
			int x = ((Integer)get(xMouseECD)).intValue();
			int prevX = ((Integer)getPreviousTransactionState(xMouseECD)).intValue();
			int deltaX = x - prevX;
			int y = ((Integer)get(yMouseECD)).intValue();
			int prevY = ((Integer)getPreviousTransactionState(yMouseECD)).intValue();
			int deltaY = y - prevY;
			
			for (int i=0 ; i < selectedNodes.length ; i++)
			{
				if (selectedNodes[i])
				{
					tops[i] += deltaY;
					lefts[i] += deltaX;
					bottoms[i] += deltaY;
					rights[i] += deltaX;
				}
			}
			
			set(pixelNodeTLBRECD, IntIlmFromArray.create(tlbr));
		}
	}
}