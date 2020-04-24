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
package tfw.plot;

import java.awt.Color;
import tfw.awt.ecd.ColorECD;
import tfw.awt.ecd.GraphicECD;
import tfw.awt.graphic.FillRectGraphic;
import tfw.awt.graphic.Graphic;
import tfw.awt.graphic.SetColorGraphic;
import tfw.tsm.Converter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.IntegerECD;

public class BackgroundGraphicConverter extends Converter
{
	private final ColorECD colorECD;
	private final IntegerECD widthECD;
	private final IntegerECD heightECD;
	private final GraphicECD graphicECD;
	
	public BackgroundGraphicConverter(String name, ColorECD colorECD,
		IntegerECD widthECD, IntegerECD heightECD, GraphicECD graphicECD)
	{
		super("BackgroundConverter["+name+"]",
			new ObjectECD[] {colorECD, widthECD, heightECD},
			null,
			new ObjectECD[] {graphicECD});
		
		this.colorECD = colorECD;
		this.widthECD = widthECD;
		this.heightECD = heightECD;
		this.graphicECD = graphicECD;
	}
		
	protected void convert()
	{
		Color color = (Color)get(colorECD);
		int width = ((Integer)get(widthECD)).intValue();
		int height = ((Integer)get(heightECD)).intValue();
		
		Graphic scg = SetColorGraphic.create(null, color);
		set(graphicECD, FillRectGraphic.create(scg, 0, 0, width, height));
	}
}
