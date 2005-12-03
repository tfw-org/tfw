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
package tfw.plot;

import java.awt.Image;
import java.awt.image.ImageObserver;

import tfw.awt.ecd.GraphicECD;
import tfw.awt.ecd.ImageECD;
import tfw.awt.ecd.ImageObserverECD;
import tfw.awt.graphic.DrawImageGraphic;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class DrawImageConverter extends TriggeredConverter
{
	private final ImageECD imageECD;
	private final IntegerECD xECD;
	private final IntegerECD yECD;
	private final ImageObserverECD imageObserverECD;
	private final GraphicECD graphicECD;
	
	public DrawImageConverter(String name, StatelessTriggerECD triggerECD,
		ImageECD imageECD, IntegerECD xECD, IntegerECD yECD,
		ImageObserverECD imageObserverECD, GraphicECD graphicECD)
	{
		super("DrawImageConverter["+name+"]",
			triggerECD,
			new ObjectECD[] {imageECD, xECD, yECD,
				imageObserverECD},
			new ObjectECD[] {graphicECD});
		
		this.imageECD = imageECD;
		this.xECD = xECD;
		this.yECD = yECD;
		this.imageObserverECD = imageObserverECD;
		this.graphicECD = graphicECD;
	}
	
	protected void convert()
	{
		Image image = (Image)get(imageECD);
		int x = ((Integer)get(xECD)).intValue();
		int y = ((Integer)get(yECD)).intValue();
		ImageObserver imageObserver = (ImageObserver)get(imageObserverECD);
		
		set(graphicECD, DrawImageGraphic.create(null, image, x, y, imageObserver));
	}
}
