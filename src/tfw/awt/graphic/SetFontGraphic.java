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
package tfw.awt.graphic;

import java.awt.Font;
import java.awt.Graphics2D;

public final class SetFontGraphic
{
	private SetFontGraphic() {}
	
	public static Graphic create(Graphic graphic, Font font)
	{
		return(new MyGraphic(graphic, font));
	}
	
	private static class MyGraphic implements Graphic
	{
		private final Graphic graphic;
		private final Font font;
		
		public MyGraphic(Graphic graphic, Font font)
		{
			this.graphic = graphic;
			this.font = font;
		}
		
		public void paint(Graphics2D graphics2D)
		{
			if (graphic != null)
			{
				graphic.paint(graphics2D);
			}
			graphics2D.setFont(font);
		}
	}
}