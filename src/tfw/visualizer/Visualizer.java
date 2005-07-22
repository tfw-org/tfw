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
package tfw.visualizer;

import java.awt.Color;

import javax.swing.JPanel;

import tfw.awt.ecd.ColorECD;
import tfw.awt.ecd.FontECD;
import tfw.awt.ecd.GraphicECD;
import tfw.awt.event.ComponentInitiator;
import tfw.awt.event.MouseInitiator;
import tfw.awt.event.WindowInitiator;
import tfw.plot.BackgroundGraphicConverter;
import tfw.plot.PlotPanel;
import tfw.swing.JFrameBB;
import tfw.swing.JMenuBB;
import tfw.swing.JMenuBarBB;
import tfw.swing.JMenuItemBB;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TreeComponent;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.ila.DoubleIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class Visualizer extends JFrameBB
{
	private static final ColorECD BACKGROUND_COLOR_ECD =
		new ColorECD("backgroundColor");
	private static final BooleanECD BUTTON_ONE_ECD =
		new BooleanECD("buttonOne");
	private static final BooleanECD BUTTON_TWO_ECD =
		new BooleanECD("buttonTwo");
	private static final BooleanECD BUTTON_THREE_ECD =
		new BooleanECD("buttonThree");
	private static final ObjectIlaECD EDGE_FROMS_ECD =
		new ObjectIlaECD("edgeFroms");
	private static final ObjectIlaECD EDGE_TOS_ECD =
		new ObjectIlaECD("edgeTos");
    private static final BooleanECD EXIT_ENABLED_ECD =
        new BooleanECD("exitEnabled");
    private static final StatelessTriggerECD EXIT_TRIGGER_ECD =
        new StatelessTriggerECD("exitTrigger");
    private static final FontECD FONT_ECD =
    	new FontECD("font");
    private static final StatelessTriggerECD GENERATE_GRAPHIC_TRIGGER_ECD =
    	new StatelessTriggerECD("generateGraphicTrigger");
    private static final IntegerECD GRAPH_HEIGHT_ECD =
    	new IntegerECD("graphHeight");
    private static final IntegerECD GRAPH_WIDTH_ECD =
    	new IntegerECD("graphWidth");
    private static final GraphicECD GRAPHIC_ECD =
    	new GraphicECD("graphic");
    private static final IntegerECD HEIGHT_ECD =
    	new IntegerECD("height");
    private static final ObjectIlaECD MULTI_GRAPHIC_ECD =
    	new ObjectIlaECD("multiGraphic");
    private static final ObjectIlaECD NODES_ECD =
    	new ObjectIlaECD("nodes");
    private static final DoubleIlaECD NODES_X_ECD =
    	new DoubleIlaECD("nodeXs");
    private static final DoubleIlaECD NODES_Y_ECD =
    	new DoubleIlaECD("nodeYs");
    private static final BooleanECD REFRESH_ENABLED_ECD =
    	new BooleanECD("refreshEnabled");
    private static final StatelessTriggerECD REFRESH_TRIGGER_ECD =
    	new StatelessTriggerECD("refreshTrigger");
    private static final IntegerECD WIDTH_ECD =
    	new IntegerECD("width");
    private static final IntegerECD X_OFFSET_ECD =
    	new IntegerECD("xOffset");
    private static final IntegerECD X_MOUSE_ECD =
    	new IntegerECD("xMouse");
    private static final IntegerECD Y_OFFSET_ECD =
    	new IntegerECD("yOffset");
    private static final IntegerECD Y_MOUSE_ECD =
    	new IntegerECD("yMouse");
    
    public Visualizer(TreeComponent treeComponent)
    {
        super(createRoot("Visualizer["+treeComponent.getName()+"]"));
        
        JMenuItemBB refreshMI = new JMenuItemBB("Refresh", REFRESH_TRIGGER_ECD,
        	REFRESH_ENABLED_ECD);
        refreshMI.setText("Refresh");
        JMenuItemBB exitMI = new JMenuItemBB("Exit", EXIT_TRIGGER_ECD,
            EXIT_ENABLED_ECD);
        exitMI.setText("Exit");
        
        JMenuBB fileM = new JMenuBB("File");
        fileM.setText("File");
        fileM.addToBoth(refreshMI);
        fileM.addToBoth(exitMI);
        
        JMenuBarBB menuBar = new JMenuBarBB("Visualizer");
        menuBar.addToBoth(fileM);
        
        setJMenuBarForBoth(menuBar);
        
        addWindowListenerToBoth(new WindowInitiator("Visualizer", null, null,
            EXIT_TRIGGER_ECD, null, null, null, null));
        
        getBranch().add(new ExitConverter(EXIT_TRIGGER_ECD));
        getBranch().add(new NodeEdgeConverter(
        	(Root)treeComponent, REFRESH_TRIGGER_ECD, NODES_ECD, NODES_X_ECD,
			NODES_Y_ECD, EDGE_FROMS_ECD, EDGE_TOS_ECD));
        getBranch().add(new MovePlotConverter(X_MOUSE_ECD, Y_MOUSE_ECD,
        	BUTTON_ONE_ECD, BUTTON_TWO_ECD, BUTTON_THREE_ECD,
        	X_OFFSET_ECD, Y_OFFSET_ECD));
        getBranch().add(new ResizePlotConverter(X_MOUSE_ECD, Y_MOUSE_ECD,
            BUTTON_ONE_ECD, BUTTON_TWO_ECD, BUTTON_THREE_ECD,
            GRAPH_WIDTH_ECD, GRAPH_HEIGHT_ECD));
        
        PlotPanel plotPanel = new PlotPanel("Visualizer");
        
		plotPanel.addComponentListenerToBoth(new ComponentInitiator(
			"PlotPanel", null, null, null, WIDTH_ECD, HEIGHT_ECD));
				MouseInitiator mouseInitiator = new MouseInitiator("PlotPanel",
			X_MOUSE_ECD, Y_MOUSE_ECD, BUTTON_ONE_ECD, BUTTON_TWO_ECD,
			BUTTON_THREE_ECD, null);
		plotPanel.addMouseListenerToBoth(mouseInitiator);
		plotPanel.addMouseMotionListener(mouseInitiator);
        
		BackgroundGraphicConverter backgroundGraphicConverter =
			new BackgroundGraphicConverter("PlotPanel", BACKGROUND_COLOR_ECD,
			WIDTH_ECD, HEIGHT_ECD, GRAPHIC_ECD);
		plotPanel.addGraphicProducer(backgroundGraphicConverter, 0);
		NodeEdgeToGraphicConverter nodeEdgeToGraphicConverter =
			new NodeEdgeToGraphicConverter(this, NODES_ECD, NODES_X_ECD,
			NODES_Y_ECD, X_OFFSET_ECD, Y_OFFSET_ECD, GRAPH_WIDTH_ECD,
			GRAPH_HEIGHT_ECD, FONT_ECD, GRAPHIC_ECD);
		plotPanel.addGraphicProducer(nodeEdgeToGraphicConverter, 1);
		
        setContentPaneForBoth(plotPanel);
    }
    
    private static Root createRoot(String name)
    {
        RootFactory rf = new RootFactory();
        
        rf.setLogging(true);
        rf.addTerminator(BACKGROUND_COLOR_ECD, Color.white);
        rf.addTerminator(BUTTON_ONE_ECD);
        rf.addTerminator(BUTTON_TWO_ECD);
        rf.addTerminator(BUTTON_THREE_ECD);
        rf.addTerminator(EDGE_FROMS_ECD);
        rf.addTerminator(EDGE_TOS_ECD);
        rf.addTerminator(EXIT_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(EXIT_TRIGGER_ECD);
        rf.addTerminator(FONT_ECD, new JPanel().getFont());
        rf.addTerminator(GENERATE_GRAPHIC_TRIGGER_ECD);
        rf.addTerminator(GRAPH_HEIGHT_ECD, new Integer(500));
        rf.addTerminator(GRAPH_WIDTH_ECD, new Integer(1500));
        rf.addTerminator(HEIGHT_ECD);
        rf.addTerminator(MULTI_GRAPHIC_ECD);
        rf.addTerminator(NODES_ECD);
        rf.addTerminator(NODES_X_ECD);
        rf.addTerminator(NODES_Y_ECD);
        rf.addTerminator(REFRESH_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(REFRESH_TRIGGER_ECD);
        rf.addTerminator(WIDTH_ECD);
        rf.addTerminator(X_OFFSET_ECD, new Integer(0));
        rf.addTerminator(X_MOUSE_ECD);
        rf.addTerminator(Y_OFFSET_ECD, new Integer(0));
        rf.addTerminator(Y_MOUSE_ECD);
        
        return(rf.create(name, new BasicTransactionQueue()));
    }
    
    public static void main(String[] args)
    {
        Visualizer v = new Visualizer(new Root("TestBranch"));
        
        Visualizer v2 = new Visualizer(v.getBranch());
        v2.setSize(500, 500);
        v2.setVisible(true);
    }
}