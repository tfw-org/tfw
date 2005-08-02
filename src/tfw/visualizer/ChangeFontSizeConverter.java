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

import java.awt.Font;

import tfw.awt.ecd.FontECD;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;

public class ChangeFontSizeConverter extends TriggeredConverter
{
	private final FontECD fontECD;
	private final float scale;
	
	public ChangeFontSizeConverter(String name, StatelessTriggerECD triggerECD,
		FontECD fontECD, float scale)
	{
		super("IncreaseFontConverter["+name+"]",
			triggerECD,
			new EventChannelDescription[] {fontECD},
			new EventChannelDescription[] {fontECD});
		
		this.fontECD = fontECD;
		this.scale = scale;
	}
	
	protected void convert()
	{
		Font font = (Font)get(fontECD);
		
		set(fontECD, font.deriveFont(font.getSize2D() + scale));
	}
}