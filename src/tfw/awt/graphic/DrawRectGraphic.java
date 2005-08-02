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
import tfw.check.Argument;

public class DrawRectGraphic
{
	private DrawRectGraphic() {}
	
	public static Graphic create(Graphic graphic, int x, int y,
		int width, int height)
	{
		return(new MyGraphic(graphic, x, y, width, height));
	}
	
	private static class MyGraphic implements Graphic
	{
		private final Graphic graphic;
		private final int x;
		private final int y;
		private final int width;
		private final int height;
		
		public MyGraphic(Graphic graphic, int x, int y, int width, int height)
		{
			this.graphic = graphic;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		public void paint(Graphics2D graphics2D)
		{
			if (graphic != null)
			{
				graphic.paint(graphics2D);
			}
			
			graphics2D.drawRect(x, y, width, height);
		}
	}
	
	public static Graphic create(Graphic graphic, int[] xs, int[] ys,
		int[] widths, int[] heights)
	{
		return(new MyGraphicArray(graphic, xs, ys, widths, heights));
	}
	
	private static class MyGraphicArray implements Graphic
	{
		private final Graphic graphic;
		private final int[] xs;
		private final int[] ys;
		private final int[] widths;
		private final int[] heights;
		
		public MyGraphicArray(Graphic graphic, int[] xs, int[] ys,
			int[] widths, int[] heights)
		{
			Argument.assertEquals(xs.length, ys.length,
				"xs.length", "ys.length");
			Argument.assertEquals(xs.length, widths.length,
				"xs.length", "widths.length");
			Argument.assertEquals(xs.length, heights.length,
				"xs.length", "heights.length");
					
			this.graphic = graphic;
			this.xs = xs;
			this.ys = ys;
			this.widths = widths;
			this.heights = heights;
		}
		
		public void paint(Graphics2D graphics2D)
		{
			if (graphic != null)
			{
				graphic.paint(graphics2D);
			}
			
			for (int i=0 ; i < xs.length ; i++)
			{
				graphics2D.drawRect(xs[i], ys[i], widths[i], heights[i]);
			}
		}
	}
}