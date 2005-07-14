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
package tfw.swing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import tfw.awt.ecd.ColorModelECD;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromByteIlm;
import tfw.immutable.ilm.byteilm.ByteIlm;
import tfw.tsm.Branch;
import tfw.tsm.Commit;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ilm.ByteIlmECD;

public class ByteInterleavedImagePanel extends JPanelBB
{
	private int x = 0;
	private int y = 0;
	private BufferedImage image = new BufferedImage(
		1, 1, BufferedImage.TYPE_INT_RGB);
	
	public ByteInterleavedImagePanel(String name, IntegerECD xECD,
		IntegerECD yECD, ByteIlmECD byteIlmECD, ColorModelECD colorModelECD)
	{
		super(new Branch("ByteInterleavedImagePanel["+name+"]"));
		
		getBranch().add(new ImagePanelCommit(
			xECD, yECD, byteIlmECD, colorModelECD));
		
		setOpaque(false);
	}
	
	private class ImagePanelCommit extends Commit
	{
		private final IntegerECD xECD;
		private final IntegerECD yECD;
		private final ByteIlmECD byteIlmECD;
		private final ColorModelECD colorModelECD;
		
		public ImagePanelCommit(IntegerECD xECD, IntegerECD yECD,
			ByteIlmECD byteIlmECD, ColorModelECD colorModelECD)
		{
			super("ByteInterleavedImagePanelCommit",
				new EventChannelDescription[] {xECD, yECD, byteIlmECD,
					colorModelECD});
			
			this.xECD = xECD;
			this.yECD = yECD;
			this.byteIlmECD = byteIlmECD;
			this.colorModelECD = colorModelECD;
		}
		
		protected void commit()
		{
			ByteIlm byteIlm = (ByteIlm)get(byteIlmECD);
			int width = (int)byteIlm.width();
			int height = (int)byteIlm.height();
			ColorModel colorModel = (ColorModel)get(colorModelECD);
			ByteIla byteIla = ByteIlaFromByteIlm.create(byteIlm);
			
			DataBufferByte dbb = null;
			
			try
			{
				dbb = new DataBufferByte(
					byteIla.toArray(), (int)byteIla.length());
			}
			catch(DataInvalidException die)
			{
				return;
			}
			WritableRaster wr = Raster.createInterleavedRaster(dbb,
				width, height, width, 1, new int[] {0}, null);
			
			synchronized(ByteInterleavedImagePanel.this)
			{
				x = ((Integer)get(xECD)).intValue();
				y = ((Integer)get(yECD)).intValue();				
				image = new BufferedImage(colorModel, wr, false, null);
			}
			
			repaint();
		}
	}
	
	public final void paint(Graphics g)
	{
		synchronized(this)
		{
			g.drawImage(image, x, y, this);
		}
	}
}