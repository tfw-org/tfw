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

import java.awt.Graphics2D;

public final class DrawLineGraphic
{
	private DrawLineGraphic() {}
	
	public static Graphic create(Graphic graphic,
		int[] x1s, int[] y1s, int[] x2s, int[] y2s)
	{
		return(new MyGraphic(graphic, x1s, y1s, x2s, y2s));
	}
	
	private static class MyGraphic implements Graphic
	{
		private final Graphic graphic;
		private final int[] x1s;
		private final int[] y1s;
		private final int[] x2s;
		private final int[] y2s;
		
		public MyGraphic(Graphic graphic, int[] x1s, int[] y1s,
			int[] x2s, int[] y2s)
		{
			this.graphic = graphic;
			this.x1s = x1s;
			this.y1s = y1s;
			this.x2s = x2s;
			this.y2s = y2s;
		}
		
		public void paint(Graphics2D graphics2D)
		{
			if (graphic != null)
			{
				graphic.paint(graphics2D);
			}
			for (int i=0 ; i < x1s.length ; i++)
			{
				graphics2D.drawLine(x1s[i], y1s[i], x2s[i], y2s[i]);
			}
		}
	}
}