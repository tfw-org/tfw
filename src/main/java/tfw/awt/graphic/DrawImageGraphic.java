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
package tfw.awt.graphic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public final class DrawImageGraphic
{
	private DrawImageGraphic() {}
	
	public static Graphic create(Graphic graphic, Image img, int x, int y,
		ImageObserver observer)
	{
		return(new MyGraphic(graphic, img, x, y, observer));
	}
	
	private static class MyGraphic implements Graphic
	{
		private final Graphic graphic;
		private final Image img;
		private final int x;
		private final int y;
		private final ImageObserver observer;
		
		public MyGraphic(Graphic graphic, Image img, int x, int y,
			ImageObserver observer)
		{
			this.graphic = graphic;
			this.img = img;
			this.x = x;
			this.y = y;
			this.observer = observer;
		}
		
		public void paint(Graphics2D graphics2D)
		{
			if (graphic != null)
			{
				graphic.paint(graphics2D);
			}
			graphics2D.drawImage(img, x, y, observer);
		}
	}
}