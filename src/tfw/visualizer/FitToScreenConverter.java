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

import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class FitToScreenConverter extends TriggeredConverter
{
	private final IntegerECD widthECD;
	private final IntegerECD heightECD;
	private final IntegerECD xECD;
	private final IntegerECD yECD;
	private final IntegerECD graphWidthECD;
	private final IntegerECD graphHeightECD;
	
	public FitToScreenConverter(StatelessTriggerECD triggerECD,
		IntegerECD widthECD, IntegerECD heightECD, IntegerECD xECD,
		IntegerECD yECD, IntegerECD graphWidthECD, IntegerECD graphHeightECD)
	{
		super("FitToScreenConverter",
			triggerECD,
			new EventChannelDescription[] {widthECD, heightECD},
			new EventChannelDescription[] {xECD, yECD, graphWidthECD,
				graphHeightECD});
		
		this.widthECD = widthECD;
		this.heightECD = heightECD;
		this.xECD = xECD;
		this.yECD = yECD;
		this.graphWidthECD = graphWidthECD;
		this.graphHeightECD = graphHeightECD;
	}
	
	protected void convert()
	{
		set(xECD, new Integer(0));
		set(yECD, new Integer(0));
		set(graphWidthECD, get(widthECD));
		set(graphHeightECD, get(heightECD));
	}
}