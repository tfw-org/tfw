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
package tfw.plot.test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import tfw.awt.ecd.ColorECD;
import tfw.awt.ecd.GraphicECD;
import tfw.awt.ecd.ImageECD;
import tfw.awt.ecd.ImageObserverECD;
import tfw.awt.event.ComponentInitiator;
import tfw.plot.PlotPanel;
import tfw.swing.JFrameBB;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public final class PlotPanelTest
{
	private PlotPanelTest() {}
	
	public static void main(String[] args)
	{
		
		final ColorECD BACKGROUND_COLOR_ECD =
			new ColorECD("backgroundColor");
		final StatelessTriggerECD GENERATE_GRAPHIC_TRIGGER_ECD =
			new StatelessTriggerECD("generateGraphicTrigger");
		final GraphicECD GRAPHIC_ECD =
			new GraphicECD("graphic");
		final IntegerECD HEIGHT_ECD =
			new IntegerECD("height");
		final ImageECD IMAGE_ECD =
			new ImageECD("image");
		final ImageObserverECD IMAGE_OBSERVER_ECD =
			new ImageObserverECD("imageObserver");
		final IntegerECD IMAGE_X_ECD =
			new IntegerECD("imageX");
		final IntegerECD IMAGE_Y_ECD =
			new IntegerECD("imageY");
		final tfw.tsm.ecd.ObjectIlaECD MULTI_GRAPHIC_ECD =
			new tfw.tsm.ecd.ObjectIlaECD("multiGraphic");
		final IntegerECD WIDTH_ECD =
			new IntegerECD("width");

		JFrameBB frame = new JFrameBB("PlotPanelTest");
		
		RootFactory rf2 = new RootFactory();
//		rf2.setLogging(true);
		rf2.addTerminator(BACKGROUND_COLOR_ECD, Color.blue);
		rf2.addTerminator(GENERATE_GRAPHIC_TRIGGER_ECD);
		rf2.addTerminator(HEIGHT_ECD);
		rf2.addTerminator(IMAGE_ECD, new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB));
		rf2.addTerminator(IMAGE_OBSERVER_ECD, frame);
		rf2.addTerminator(IMAGE_X_ECD, new Integer(10));
		rf2.addTerminator(IMAGE_Y_ECD, new Integer(10));
		rf2.addTerminator(MULTI_GRAPHIC_ECD);
		rf2.addTerminator(WIDTH_ECD);
		Root root2 = rf2.create("PlotPanelTest", new BasicTransactionQueue());
		
		PlotPanel plotPanel = new PlotPanel(root2);
		plotPanel.addComponentListenerToBoth(new ComponentInitiator(
			"PlotPanel", null, null, null, WIDTH_ECD, HEIGHT_ECD));
		
//		BackgroundGraphicConverter backgroundConverter = new BackgroundGraphicConverter(
//			"PlotPanel", GENERATE_GRAPHIC_TRIGGER_ECD, BACKGROUND_COLOR_ECD,
//			WIDTH_ECD, HEIGHT_ECD, GRAPHIC_ECD);
//		plotPanel.addGraphicProducer(backgroundConverter, 0);
//		
//		DrawImageConverter drawImageConverter = new DrawImageConverter(
//			"PlotPanel", GENERATE_GRAPHIC_TRIGGER_ECD, IMAGE_ECD, IMAGE_X_ECD,
//			IMAGE_Y_ECD, IMAGE_OBSERVER_ECD, GRAPHIC_ECD);
//		plotPanel.addGraphicProducer(drawImageConverter, 1);
		
		frame.setContentPane(plotPanel);
		
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}