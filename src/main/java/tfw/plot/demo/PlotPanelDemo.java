package tfw.plot.demo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import tfw.awt.ecd.ColorECD;
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
import tfw.tsm.ecd.ila.ObjectIlaECD;

public final class PlotPanelDemo {
    private PlotPanelDemo() {}

    public static void main(String[] args) {

        final ColorECD BACKGROUND_COLOR_ECD = new ColorECD("backgroundColor");
        final StatelessTriggerECD GENERATE_GRAPHIC_TRIGGER_ECD = new StatelessTriggerECD("generateGraphicTrigger");
        final IntegerECD HEIGHT_ECD = new IntegerECD("height");
        final ImageECD IMAGE_ECD = new ImageECD("image");
        final ImageObserverECD IMAGE_OBSERVER_ECD = new ImageObserverECD("imageObserver");
        final IntegerECD IMAGE_X_ECD = new IntegerECD("imageX");
        final IntegerECD IMAGE_Y_ECD = new IntegerECD("imageY");
        final ObjectIlaECD MULTI_GRAPHIC_ECD = new ObjectIlaECD("multiGraphic");
        final IntegerECD WIDTH_ECD = new IntegerECD("width");

        JFrameBB frame = new JFrameBB("PlotPanelTest");

        RootFactory rf2 = new RootFactory();
        //		rf2.setLogging(true);
        rf2.addEventChannel(BACKGROUND_COLOR_ECD, Color.blue);
        rf2.addEventChannel(GENERATE_GRAPHIC_TRIGGER_ECD);
        rf2.addEventChannel(HEIGHT_ECD);
        rf2.addEventChannel(IMAGE_ECD, new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB));
        rf2.addEventChannel(IMAGE_OBSERVER_ECD, frame);
        rf2.addEventChannel(IMAGE_X_ECD, 10);
        rf2.addEventChannel(IMAGE_Y_ECD, 10);
        rf2.addEventChannel(MULTI_GRAPHIC_ECD);
        rf2.addEventChannel(WIDTH_ECD);
        Root root2 = rf2.create("PlotPanelTest", new BasicTransactionQueue());

        PlotPanel plotPanel = new PlotPanel(root2);
        plotPanel.addComponentListenerToBoth(
                new ComponentInitiator("PlotPanel", null, null, null, WIDTH_ECD, HEIGHT_ECD));

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
