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
 * without even the implied warranty of
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
import tfw.immutable.ila.booleanila.BooleanIlaFill;
import tfw.immutable.ila.booleanila.BooleanIlaMutate;
import tfw.immutable.ilm.intilm.IntIlm;
import tfw.tsm.Converter;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ila.BooleanIlaECD;
import tfw.tsm.ecd.ilm.IntIlmECD;

public class SelectionConverter extends Converter
{
	private final BooleanECD selectedECD;
	private final BooleanECD buttonOneECD;
	private final BooleanECD buttonTwoECD;
	private final BooleanECD buttonThreeECD;
	private final IntIlmECD pixelNodeTLBRECD;
	private final IntegerECD xMouseECD;
	private final IntegerECD yMouseECD;
	private final BooleanECD controlKeyPressedECD;
	private final BooleanIlaECD selectedNodesECD;
	
	public SelectionConverter(BooleanECD selectedECD, BooleanECD buttonOneECD,
		BooleanECD buttonTwoECD, BooleanECD buttonThreeECD,
		IntIlmECD pixelNodeTLBRECD, IntegerECD xMouseECD, IntegerECD yMouseECD,
		BooleanECD controlKeyPressedECD, BooleanIlaECD selectedNodesECD)
	{
		super("SelectionConverter",
			new ObjectECD[] {selectedECD, buttonOneECD,
				buttonTwoECD, buttonThreeECD},
			new ObjectECD[] {pixelNodeTLBRECD, xMouseECD,
				yMouseECD, controlKeyPressedECD, selectedNodesECD},
			new ObjectECD[] {selectedNodesECD});
		
		this.selectedECD = selectedECD;
		this.buttonOneECD = buttonOneECD;
		this.buttonTwoECD = buttonTwoECD;
		this.buttonThreeECD = buttonThreeECD;
		this.pixelNodeTLBRECD = pixelNodeTLBRECD;
		this.xMouseECD = xMouseECD;
		this.yMouseECD = yMouseECD;
		this.controlKeyPressedECD = controlKeyPressedECD;
		this.selectedNodesECD = selectedNodesECD;
	}
	
	protected void convert()
	{
		if (((Boolean)get(selectedECD)).booleanValue() &&
			((Boolean)get(buttonOneECD)).booleanValue() &&
			!((Boolean)getPreviousTransactionState(buttonOneECD)).booleanValue() &&
			!((Boolean)get(buttonTwoECD)).booleanValue() &&
			!((Boolean)get(buttonThreeECD)).booleanValue())
		{
			int x = ((Integer)get(xMouseECD)).intValue();
			int y = ((Integer)get(yMouseECD)).intValue();
			
			int[][] tlbr = null;
			int width = -1;
			
			try
			{
				IntIlm tlbrIlm = (IntIlm)get(pixelNodeTLBRECD);
				
				tlbr = tlbrIlm.toArray();
				width = (int)tlbrIlm.width();
			}
			catch(DataInvalidException die)
			{
				return;
			}
			
			int[] tops = tlbr[0];
			int[] lefts = tlbr[1];
			int[] bottoms = tlbr[2];
			int[] rights = tlbr[3];
			BooleanIla ila = BooleanIlaFill.create(false, width);
			
			if (((Boolean)get(controlKeyPressedECD)).booleanValue())
			{
				ila = (BooleanIla)get(selectedNodesECD);
			}
			else
			{
				ila = BooleanIlaFill.create(false, width);
			}
			
			for (int i=width-1 ; i >= 0 ; i--)
			{
				if (tops[i] <= y && y <= bottoms[i] &&
					lefts[i] <= x && x <= rights[i])
				{
					try
					{
						ila = BooleanIlaMutate.create(ila, i, !ila.toArray(i, 1)[0]);
					}
					catch (DataInvalidException die)
					{
						return;
					}
					break;
				}
			}
			
			set(selectedNodesECD, ila);
		}
	}
}