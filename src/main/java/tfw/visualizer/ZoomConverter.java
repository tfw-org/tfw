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

import java.awt.Font;

import tfw.awt.ecd.FontECD;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class ZoomConverter extends TriggeredConverter
{
	private final FontECD fontECD;
	private final IntegerECD graphWidthECD;
	private final IntegerECD graphHeightECD;
	private final float scale;
	
	public ZoomConverter(String name, StatelessTriggerECD triggerECD,
		FontECD fontECD, IntegerECD graphWidthECD, IntegerECD graphHeightECD,
		float scale)
	{
		super("ZoomConverter["+name+"]",
			triggerECD,
			new ObjectECD[] {fontECD, graphWidthECD,
				graphHeightECD},
			new ObjectECD[] {fontECD, graphWidthECD,
				graphHeightECD});
		
		this.fontECD = fontECD;
		this.graphWidthECD = graphWidthECD;
		this.graphHeightECD = graphHeightECD;
		this.scale = scale;
	}
	
	protected void convert()
	{
		Font font = (Font)get(fontECD);
		float newSize = (float)Math.floor(font.getSize2D() * scale);
		float graphWidth = ((Integer)get(graphWidthECD)).intValue();
		float graphHeight = ((Integer)get(graphHeightECD)).intValue();
		
		if (newSize > 0.0f)
		{
			set(fontECD, font.deriveFont(newSize));
		}
		
		set(graphWidthECD, new Integer((int)(graphWidth * scale)));
		set(graphHeightECD, new Integer((int)(graphHeight * scale)));
	}
}