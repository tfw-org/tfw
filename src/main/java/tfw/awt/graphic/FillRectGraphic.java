package tfw.awt.graphic;

import java.awt.Graphics2D;
import tfw.check.Argument;

public class FillRectGraphic
{
	private FillRectGraphic() {}
	
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
			
			graphics2D.fillRect(x, y, width, height);
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
				graphics2D.fillRect(xs[i], ys[i], widths[i], heights[i]);
			}
		}
	}
}