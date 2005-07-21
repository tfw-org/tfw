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

import tfw.tsm.Converter;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;

public class ResizePlotConverter extends Converter
{
	private final IntegerECD xECD;
	private final IntegerECD yECD;
	private final BooleanECD button1ECD;
	private final BooleanECD button2ECD;
	private final BooleanECD button3ECD;
	private final IntegerECD graphWidthECD;
	private final IntegerECD graphHeightECD;
	
	public ResizePlotConverter(IntegerECD xECD, IntegerECD yECD,
		BooleanECD button1ECD, BooleanECD button2ECD, BooleanECD button3ECD,
		IntegerECD graphWidthECD, IntegerECD graphHeightECD)
	{
		super("ResizePlotConverter",
			new EventChannelDescription[] {xECD, yECD,
				button1ECD, button2ECD, button3ECD},
			new EventChannelDescription[] {graphWidthECD, graphHeightECD},
			new EventChannelDescription[] {graphWidthECD, graphHeightECD});
		
		this.xECD = xECD;
		this.yECD = yECD;
		this.button1ECD = button1ECD;
		this.button2ECD = button2ECD;
		this.button3ECD = button3ECD;
		this.graphWidthECD = graphWidthECD;
		this.graphHeightECD = graphHeightECD;
	}
	
	protected void convert()
	{
		boolean button1 = ((Boolean)get(button1ECD)).booleanValue();
		boolean button2 = ((Boolean)get(button2ECD)).booleanValue();
		boolean button3 = ((Boolean)get(button3ECD)).booleanValue();

		if (!button1 && !button2 && button3)
		{
			int graphWidth = ((Integer)get(graphWidthECD)).intValue();
			int graphHeight = ((Integer)get(graphHeightECD)).intValue();
			
			set(graphWidthECD, new Integer(graphWidth+1));
			set(graphHeightECD, new Integer(graphHeight+1));
		}
	}
}