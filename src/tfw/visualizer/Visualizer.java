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
import tfw.swing.JCheckBoxMenuItemBB;
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
import tfw.tsm.ecd.ila.IntIlaECD;
import tfw.tsm.ecd.ila.LongIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class Visualizer extends JFrameBB
{
	private static final BooleanECD BUTTON_ONE_ECD =
		new BooleanECD("buttonOne");
	private static final BooleanECD BUTTON_TWO_ECD =
		new BooleanECD("buttonTwo");
	private static final BooleanECD BUTTON_THREE_ECD =
		new BooleanECD("buttonThree");
	private static final IntIlaECD CLUSTER_XS_ECD =
		new IntIlaECD("clusterXs");
	private static final IntIlaECD CLUSTER_YS_ECD =
		new IntIlaECD("clusterYs");
	private static final IntIlaECD CLUSTER_WIDTHS_ECD =
		new IntIlaECD("clusterWidths");
	private static final IntIlaECD CLUSTER_HEIGHTS_ECD =
		new IntIlaECD("clusterHeights");
	private static final LongIlaECD EDGE_FROMS_ECD =
		new LongIlaECD("edgeFroms");
	private static final LongIlaECD EDGE_TOS_ECD =
		new LongIlaECD("edgeTos");
    private static final BooleanECD EXIT_ENABLED_ECD =
        new BooleanECD("exitEnabled");
    private static final StatelessTriggerECD EXIT_TRIGGER_ECD =
        new StatelessTriggerECD("exitTrigger");
    private static final ObjectIlaECD FILTERED_NODES_ECD =
    	new ObjectIlaECD("filteredNodes");
    private static final LongIlaECD FILTERED_EDGE_FROMS_ECD =
    	new LongIlaECD("filteredEdgeFroms");
    private static final LongIlaECD FILTERED_EDGE_TOS_ECD =
    	new LongIlaECD("filteredEdgeTos");
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
    private static final BooleanECD DECREASE_FONT_ENABLED_ECD =
    	new BooleanECD("decreaseFontEnabled");
    private static final StatelessTriggerECD DECREASE_FONT_TRIGGER_ECD =
    	new StatelessTriggerECD("decreaseFontTrigger");
    private static final BooleanECD INCREASE_FONT_ENABLED_ECD =
    	new BooleanECD("increaseFontEnabled");
    private static final StatelessTriggerECD INCREASE_FONT_TRIGGER_ECD =
    	new StatelessTriggerECD("increaseFontTrigger");
    private static final ObjectIlaECD MULTI_GRAPHIC_ECD =
    	new ObjectIlaECD("multiGraphic");
    private static final ObjectIlaECD NODE_CLUSTERS_ECD =
    	new ObjectIlaECD("nodeClusters");
    private static final ObjectIlaECD NODE_CLUSTER_FROMS_ECD =
    	new ObjectIlaECD("nodeClusterFroms");
    private static final ObjectIlaECD NODE_CLUSTER_PIXEL_XS_ECD =
    	new ObjectIlaECD("nodeClusterPixelXs");
    private static final ObjectIlaECD NODE_CLUSTER_PIXEL_YS_ECD =
    	new ObjectIlaECD("nodeClusterPixelYs");
    private static final ObjectIlaECD NODE_CLUSTER_TOS_ECD =
    	new ObjectIlaECD("nodeClusterTos");
    private static final ObjectIlaECD NODE_CLUSTER_XS_ECD =
    	new ObjectIlaECD("nodeClusterXs");
    private static final ObjectIlaECD NODE_CLUSTER_YS_ECD =
    	new ObjectIlaECD("nodeClusterYs");
    private static final ObjectIlaECD NODES_ECD =
    	new ObjectIlaECD("nodes");
    private static final DoubleIlaECD NODES_X_ECD =
    	new DoubleIlaECD("nodeXs");
    private static final DoubleIlaECD NODES_Y_ECD =
    	new DoubleIlaECD("nodeYs");
    private static final StatelessTriggerECD PROPERTIES_TRIGGER_ECD =
    	new StatelessTriggerECD("propertiesTrigger");
    private static final BooleanECD PROPERTIES_ENABLED_ECD =
    	new BooleanECD("propertiesEnabled");
    private static final BooleanECD REFRESH_ENABLED_ECD =
    	new BooleanECD("refreshEnabled");
    private static final StatelessTriggerECD REFRESH_TRIGGER_ECD =
    	new StatelessTriggerECD("refreshTrigger");
    private static final BooleanECD SHOW_BRANCHES_ENABLED_ECD =
    	new BooleanECD("showBranchesEnabled");
    private static final BooleanECD SHOW_BRANCHES_SELECTED_ECD =
    	new BooleanECD("showBranchesSelected");
    private static final BooleanECD SHOW_COMMITS_ENABLED_ECD =
    	new BooleanECD("showCommitsEnabled");
    private static final BooleanECD SHOW_COMMITS_SELECTED_ECD =
    	new BooleanECD("showCommitsSelected");
    private static final BooleanECD SHOW_CONVERTERS_ENABLED_ECD =
    	new BooleanECD("showConvertersEnabled");
    private static final BooleanECD SHOW_CONVERTERS_SELECTED_ECD =
    	new BooleanECD("showConvertersSelected");
    private static final BooleanECD SHOW_EVENT_CHANNELS_ENABLED_ECD =
    	new BooleanECD("showEventChannelsEnabled");
    private static final BooleanECD SHOW_EVENT_CHANNELS_SELECTED_ECD =
    	new BooleanECD("showEventChannelsSelected");
    private static final BooleanECD SHOW_INITIATORS_ENABLED_ECD =
    	new BooleanECD("showInitiatorsEnabled");
    private static final BooleanECD SHOW_INITIATORS_SELECTED_ECD =
    	new BooleanECD("showInitiatorsSelected");
    private static final BooleanECD SHOW_MULTIPLEXEDBRANCHES_ENABLED_ECD =
    	new BooleanECD("showMultiplexedBranchesEnabled");
    private static final BooleanECD SHOW_MULTIPLEXEDBRANCHES_SELECTED_ECD =
    	new BooleanECD("showMultiplexedBranchesSelected");
    private static final BooleanECD SHOW_ROOTS_ENABLED_ECD =
    	new BooleanECD("showRootsEnabled");
    private static final BooleanECD SHOW_ROOTS_SELECTED_ECD =
    	new BooleanECD("showRootsSelected");
    private static final BooleanECD SHOW_SYNCHRONIZERS_ENABLED_ECD =
    	new BooleanECD("showSynchronizersEnabled");
    private static final BooleanECD SHOW_SYNCHRONIZERS_SELECTED_ECD =
    	new BooleanECD("showSynchronizersSelected");
    private static final BooleanECD SHOW_TRIGGEREDCOMMITS_ENABLED_ECD =
    	new BooleanECD("showTriggeredCommitsEnabled");
    private static final BooleanECD SHOW_TRIGGEREDCOMMITS_SELECTED_ECD =
    	new BooleanECD("showTriggeredCommitsSelected");
    private static final BooleanECD SHOW_TRIGGEREDCONVERTERS_ENABLED_ECD =
    	new BooleanECD("showTriggeredConvertersEnabled");
    private static final BooleanECD SHOW_TRIGGEREDCONVERTERS_SELECTED_ECD =
    	new BooleanECD("showTriggeredConvertersSelected");
    private static final BooleanECD SHOW_VALIDATORS_ENABLED_ECD =
    	new BooleanECD("showValidatorsEnabled");
    private static final BooleanECD SHOW_VALIDATORS_SELECTED_ECD =
    	new BooleanECD("showValidatorsSelected");
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
        
        JMenuItemBB propertiesMI = new JMenuItemBB("Properties",
        	PROPERTIES_TRIGGER_ECD, PROPERTIES_ENABLED_ECD);
        propertiesMI.setText("Properties");
        
        JMenuBB editM = new JMenuBB("Edit");
        editM.setText("Edit");
        editM.addToBoth(propertiesMI);
        
        JMenuItemBB increaseFontMI = new JMenuItemBB("IncreaseFont",
        	INCREASE_FONT_TRIGGER_ECD, INCREASE_FONT_ENABLED_ECD);
        increaseFontMI.setText("Increase Font Size");
        JMenuItemBB decreaseFontMI = new JMenuItemBB("DecreaseFont",
        	DECREASE_FONT_TRIGGER_ECD, DECREASE_FONT_ENABLED_ECD);
        decreaseFontMI.setText("Decrease Font Size");
        
        JCheckBoxMenuItemBB showBranchesMI = new JCheckBoxMenuItemBB(
        	"ShowBranches", SHOW_BRANCHES_SELECTED_ECD,
        	SHOW_BRANCHES_ENABLED_ECD);
        showBranchesMI.setText("Branches");
        JCheckBoxMenuItemBB showCommitsMI = new JCheckBoxMenuItemBB(
            "ShowCommits", SHOW_COMMITS_SELECTED_ECD,
            SHOW_COMMITS_ENABLED_ECD);
        showCommitsMI.setText("Commits");
        JCheckBoxMenuItemBB showConvertersMI = new JCheckBoxMenuItemBB(
            "ShowConverters", SHOW_CONVERTERS_SELECTED_ECD,
            SHOW_CONVERTERS_ENABLED_ECD);
        showConvertersMI.setText("Converters");
        JCheckBoxMenuItemBB showEventChannelsMI = new JCheckBoxMenuItemBB(
        	"ShowEventChannels", SHOW_EVENT_CHANNELS_SELECTED_ECD,
        	SHOW_EVENT_CHANNELS_ENABLED_ECD);
        showEventChannelsMI.setText("EventChannels");
        JCheckBoxMenuItemBB showInitiatorsMI = new JCheckBoxMenuItemBB(
            "ShowInitiators", SHOW_INITIATORS_SELECTED_ECD,
            SHOW_INITIATORS_ENABLED_ECD);
        showInitiatorsMI.setText("Initiators");
        JCheckBoxMenuItemBB showMultiplexedBranchesMI = new JCheckBoxMenuItemBB(
            "ShowMultiplexedBranches", SHOW_MULTIPLEXEDBRANCHES_SELECTED_ECD,
            SHOW_MULTIPLEXEDBRANCHES_ENABLED_ECD);
        showMultiplexedBranchesMI.setText("Multiplexed Branches");
        JCheckBoxMenuItemBB showRootsMI = new JCheckBoxMenuItemBB(
            "ShowRoots", SHOW_ROOTS_SELECTED_ECD,
            SHOW_ROOTS_ENABLED_ECD);
        showRootsMI.setText("Roots");
        JCheckBoxMenuItemBB showSynchronizersMI = new JCheckBoxMenuItemBB(
            "ShowSynchronizers", SHOW_SYNCHRONIZERS_SELECTED_ECD,
            SHOW_SYNCHRONIZERS_ENABLED_ECD);
        showSynchronizersMI.setText("Synchronizers");
        JCheckBoxMenuItemBB showTriggeredCommitsMI = new JCheckBoxMenuItemBB(
            "ShowTriggeredCommits", SHOW_TRIGGEREDCOMMITS_SELECTED_ECD,
            SHOW_TRIGGEREDCOMMITS_ENABLED_ECD);
        showTriggeredCommitsMI.setText("Triggered Commits");
        JCheckBoxMenuItemBB showTriggeredConvertersMI = new JCheckBoxMenuItemBB(
            "ShowTriggeredConverters", SHOW_TRIGGEREDCONVERTERS_SELECTED_ECD,
            SHOW_TRIGGEREDCONVERTERS_ENABLED_ECD);
        showTriggeredConvertersMI.setText("Triggered Converters");
        JCheckBoxMenuItemBB showValidatorsMI = new JCheckBoxMenuItemBB(
            "ShowValidators", SHOW_VALIDATORS_SELECTED_ECD,
            SHOW_VALIDATORS_ENABLED_ECD);
        showValidatorsMI.setText("Validators");
        
        JMenuBB showM = new JMenuBB("Show");
        showM.setText("Show");
//        showM.addToBoth(showBranchesMI);
        showM.addToBoth(showCommitsMI);
        showM.addToBoth(showConvertersMI);
        showM.addToBoth(showEventChannelsMI);
        showM.addToBoth(showInitiatorsMI);
//        showM.addToBoth(showMultiplexedBranchesMI);
//        showM.addToBoth(showRootsMI);
        showM.addToBoth(showSynchronizersMI);
        showM.addToBoth(showTriggeredCommitsMI);
        showM.addToBoth(showTriggeredConvertersMI);
        showM.addToBoth(showValidatorsMI);
        
        JMenuBB viewM = new JMenuBB("View");
        viewM.setText("View");
        viewM.addToBoth(increaseFontMI);
        viewM.addToBoth(decreaseFontMI);
        viewM.addSeparator();
        viewM.addToBoth(showM);
        
        JMenuBarBB menuBar = new JMenuBarBB("Visualizer");
        menuBar.addToBoth(fileM);
        menuBar.addToBoth(editM);
        menuBar.addToBoth(viewM);
        
        setJMenuBarForBoth(menuBar);
        
        addWindowListenerToBoth(new WindowInitiator("Visualizer", null, null,
            EXIT_TRIGGER_ECD, null, null, null, null));
        
        getBranch().add(new ExitConverter(EXIT_TRIGGER_ECD));
        getBranch().add(new NodeEdgeConverter(
        	(Root)treeComponent, REFRESH_TRIGGER_ECD, NODES_ECD,
        	EDGE_FROMS_ECD, EDGE_TOS_ECD));
        getBranch().add(new FilterConverter(NODES_ECD, EDGE_FROMS_ECD,
        	EDGE_TOS_ECD, SHOW_BRANCHES_SELECTED_ECD, SHOW_COMMITS_SELECTED_ECD,
        	SHOW_CONVERTERS_SELECTED_ECD, SHOW_EVENT_CHANNELS_SELECTED_ECD,
        	SHOW_INITIATORS_SELECTED_ECD, SHOW_MULTIPLEXEDBRANCHES_SELECTED_ECD,
        	SHOW_ROOTS_SELECTED_ECD, SHOW_SYNCHRONIZERS_SELECTED_ECD,
        	SHOW_TRIGGEREDCOMMITS_SELECTED_ECD,
        	SHOW_TRIGGEREDCONVERTERS_SELECTED_ECD, SHOW_VALIDATORS_SELECTED_ECD,
        	FILTERED_NODES_ECD, FILTERED_EDGE_FROMS_ECD, FILTERED_EDGE_TOS_ECD));
        getBranch().add(new ClusterConverter(FILTERED_EDGE_FROMS_ECD,
        	FILTERED_EDGE_TOS_ECD, NODE_CLUSTERS_ECD, NODE_CLUSTER_FROMS_ECD,
        	NODE_CLUSTER_TOS_ECD));
        getBranch().add(new SimpleTreeLayoutConverter(NODE_CLUSTERS_ECD,
        	NODE_CLUSTER_FROMS_ECD, NODE_CLUSTER_TOS_ECD, NODE_CLUSTER_XS_ECD,
        	NODE_CLUSTER_YS_ECD));
		getBranch().add(new ClusterRectangleConverter(REFRESH_TRIGGER_ECD,
			X_OFFSET_ECD, Y_OFFSET_ECD, GRAPH_WIDTH_ECD, GRAPH_HEIGHT_ECD,
			NODE_CLUSTERS_ECD, CLUSTER_XS_ECD, CLUSTER_YS_ECD,
			CLUSTER_WIDTHS_ECD, CLUSTER_HEIGHTS_ECD));
	    getBranch().add(new NormalPixelConverter(CLUSTER_XS_ECD, CLUSTER_YS_ECD,
        	CLUSTER_WIDTHS_ECD, CLUSTER_HEIGHTS_ECD, NODE_CLUSTER_XS_ECD,
        	NODE_CLUSTER_YS_ECD, NODE_CLUSTER_PIXEL_XS_ECD,
        	NODE_CLUSTER_PIXEL_YS_ECD));
        getBranch().add(new MovePlotConverter(X_MOUSE_ECD, Y_MOUSE_ECD,
        	BUTTON_ONE_ECD, BUTTON_TWO_ECD, BUTTON_THREE_ECD,
        	X_OFFSET_ECD, Y_OFFSET_ECD));
        getBranch().add(new ResizePlotConverter(X_MOUSE_ECD, Y_MOUSE_ECD,
            BUTTON_ONE_ECD, BUTTON_TWO_ECD, BUTTON_THREE_ECD,
            GRAPH_WIDTH_ECD, GRAPH_HEIGHT_ECD));
        getBranch().add(new ShowPanelInNonModalDialog(PROPERTIES_TRIGGER_ECD,
        	this, "Visualizer Properties", VisualizerProperties.class));
        getBranch().add(new ChangeFontSizeConverter("Decrease",
        	DECREASE_FONT_TRIGGER_ECD, FONT_ECD, -1));
        getBranch().add(new ChangeFontSizeConverter("Increase",
            INCREASE_FONT_TRIGGER_ECD, FONT_ECD, 1));
        
        PlotPanel plotPanel = new PlotPanel("Visualizer");
        
		plotPanel.addComponentListenerToBoth(new ComponentInitiator(
			"PlotPanel", null, null, null, WIDTH_ECD, HEIGHT_ECD));
				MouseInitiator mouseInitiator = new MouseInitiator("PlotPanel",
			X_MOUSE_ECD, Y_MOUSE_ECD, BUTTON_ONE_ECD, BUTTON_TWO_ECD,
			BUTTON_THREE_ECD);
		plotPanel.addMouseListenerToBoth(mouseInitiator);
		plotPanel.addMouseMotionListener(mouseInitiator);
       
		BackgroundGraphicConverter backgroundGraphicConverter =
			new BackgroundGraphicConverter("PlotPanel",
			VisualizerProperties.BACKGROUND_COLOR_ECD,
			WIDTH_ECD, HEIGHT_ECD, GRAPHIC_ECD);
		plotPanel.addGraphicProducer(backgroundGraphicConverter, 0);
		EdgeToGraphicConverter edgeToGraphicConverter =
			new EdgeToGraphicConverter(NODE_CLUSTERS_ECD, NODE_CLUSTER_FROMS_ECD,
			NODE_CLUSTER_TOS_ECD, NODE_CLUSTER_PIXEL_XS_ECD,
			NODE_CLUSTER_PIXEL_YS_ECD, GRAPHIC_ECD);
		plotPanel.addGraphicProducer(edgeToGraphicConverter, 1);
		NodeToGraphicConverter nodeToGraphicConverter =
			new NodeToGraphicConverter(this, FILTERED_NODES_ECD, NODE_CLUSTERS_ECD,
			NODE_CLUSTER_PIXEL_XS_ECD, NODE_CLUSTER_PIXEL_YS_ECD, FONT_ECD,
			VisualizerProperties.BACKGROUND_COLOR_ECD,
			VisualizerProperties.BRANCH_COLOR_ECD,
			VisualizerProperties.COMMIT_COLOR_ECD,
			VisualizerProperties.CONVERTER_COLOR_ECD,
			VisualizerProperties.EVENTCHANNEL_COLOR_ECD,
			VisualizerProperties.INITIATOR_COLOR_ECD,
			VisualizerProperties.MULTIPLEXEDBRANCH_COLOR_ECD,
			VisualizerProperties.ROOT_COLOR_ECD,
			VisualizerProperties.SYNCHRONIZER_COLOR_ECD,
			VisualizerProperties.TRIGGEREDCOMMIT_COLOR_ECD,
			VisualizerProperties.TRIGGEREDCONVERTER_COLOR_ECD,
			VisualizerProperties.VALIDATOR_COLOR_ECD, GRAPHIC_ECD);
		plotPanel.addGraphicProducer(nodeToGraphicConverter, 2);
		
        setContentPaneForBoth(plotPanel);
    }
    
    private static Root createRoot(String name)
    {
        RootFactory rf = new RootFactory();
        
//        rf.setLogging(true);
        rf.addEventChannel(BUTTON_ONE_ECD);
        rf.addEventChannel(BUTTON_TWO_ECD);
        rf.addEventChannel(BUTTON_THREE_ECD);
        rf.addEventChannel(CLUSTER_XS_ECD);
        rf.addEventChannel(CLUSTER_YS_ECD);
        rf.addEventChannel(CLUSTER_WIDTHS_ECD);
        rf.addEventChannel(CLUSTER_HEIGHTS_ECD);
        rf.addEventChannel(EDGE_FROMS_ECD);
        rf.addEventChannel(EDGE_TOS_ECD);
        rf.addEventChannel(EXIT_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(EXIT_TRIGGER_ECD);
        rf.addEventChannel(FILTERED_NODES_ECD);
        rf.addEventChannel(FILTERED_EDGE_FROMS_ECD);
        rf.addEventChannel(FILTERED_EDGE_TOS_ECD);
        rf.addEventChannel(FONT_ECD, new JPanel().getFont());
        rf.addEventChannel(GENERATE_GRAPHIC_TRIGGER_ECD);
        rf.addEventChannel(GRAPH_HEIGHT_ECD, new Integer(500));
        rf.addEventChannel(GRAPH_WIDTH_ECD, new Integer(1500));
        rf.addEventChannel(HEIGHT_ECD);
        rf.addEventChannel(DECREASE_FONT_ENABLED_ECD);
        rf.addEventChannel(DECREASE_FONT_TRIGGER_ECD);
        rf.addEventChannel(INCREASE_FONT_ENABLED_ECD);
        rf.addEventChannel(INCREASE_FONT_TRIGGER_ECD);
        rf.addEventChannel(MULTI_GRAPHIC_ECD);
        rf.addEventChannel(NODE_CLUSTERS_ECD);
        rf.addEventChannel(NODE_CLUSTER_FROMS_ECD);
        rf.addEventChannel(NODE_CLUSTER_PIXEL_XS_ECD);
        rf.addEventChannel(NODE_CLUSTER_PIXEL_YS_ECD);
        rf.addEventChannel(NODE_CLUSTER_TOS_ECD);
        rf.addEventChannel(NODE_CLUSTER_XS_ECD);
        rf.addEventChannel(NODE_CLUSTER_YS_ECD);
        rf.addEventChannel(NODES_ECD);
        rf.addEventChannel(NODES_X_ECD);
        rf.addEventChannel(NODES_Y_ECD);
        rf.addEventChannel(PROPERTIES_TRIGGER_ECD);
        rf.addEventChannel(PROPERTIES_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(REFRESH_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(REFRESH_TRIGGER_ECD);
        rf.addEventChannel(SHOW_BRANCHES_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_BRANCHES_SELECTED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_COMMITS_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_COMMITS_SELECTED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_CONVERTERS_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_CONVERTERS_SELECTED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_EVENT_CHANNELS_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_EVENT_CHANNELS_SELECTED_ECD, Boolean.FALSE);
        rf.addEventChannel(SHOW_INITIATORS_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_INITIATORS_SELECTED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_MULTIPLEXEDBRANCHES_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_MULTIPLEXEDBRANCHES_SELECTED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_ROOTS_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_ROOTS_SELECTED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_SYNCHRONIZERS_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_SYNCHRONIZERS_SELECTED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_TRIGGEREDCOMMITS_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_TRIGGEREDCOMMITS_SELECTED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_TRIGGEREDCONVERTERS_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_TRIGGEREDCONVERTERS_SELECTED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_VALIDATORS_ENABLED_ECD, Boolean.TRUE);
        rf.addEventChannel(SHOW_VALIDATORS_SELECTED_ECD, Boolean.TRUE);
        rf.addEventChannel(WIDTH_ECD);
        rf.addEventChannel(X_OFFSET_ECD, new Integer(0));
        rf.addEventChannel(X_MOUSE_ECD);
        rf.addEventChannel(Y_OFFSET_ECD, new Integer(0));
        rf.addEventChannel(Y_MOUSE_ECD);
        
        rf.addEventChannel(VisualizerProperties.BACKGROUND_COLOR_ECD, Color.black);
        rf.addEventChannel(VisualizerProperties.BACKGROUND_COLOR_ENABLED_ECD,
        	Boolean.TRUE);
        rf.addEventChannel(VisualizerProperties.BRANCH_COLOR_ECD, Color.white);
        rf.addEventChannel(VisualizerProperties.BRANCH_COLOR_ENABLED_ECD,
        	Boolean.TRUE);
        rf.addEventChannel(VisualizerProperties.COMMIT_COLOR_ECD, Color.magenta);
        rf.addEventChannel(VisualizerProperties.COMMIT_COLOR_ENABLED_ECD,
        	Boolean.TRUE);
        rf.addEventChannel(VisualizerProperties.CONVERTER_COLOR_ECD, Color.yellow);
        rf.addEventChannel(VisualizerProperties.CONVERTER_COLOR_ENABLED_ECD,
        	Boolean.TRUE);
        rf.addEventChannel(VisualizerProperties.EVENTCHANNEL_COLOR_ECD, Color.yellow);
        rf.addEventChannel(VisualizerProperties.EVENTCHANNEL_COLOR_ENABLED_ECD,
        	Boolean.TRUE);
        rf.addEventChannel(VisualizerProperties.INITIATOR_COLOR_ECD, Color.red);
        rf.addEventChannel(VisualizerProperties.INITIATOR_COLOR_ENABLED_ECD,
        	Boolean.TRUE);
        rf.addEventChannel(VisualizerProperties.MULTIPLEXEDBRANCH_COLOR_ECD, Color.gray);
        rf.addEventChannel(VisualizerProperties.MULTIPLEXEDBRANCH_COLOR_ENABLED_ECD,
        	Boolean.TRUE);
        rf.addEventChannel(VisualizerProperties.ROOT_COLOR_ECD, Color.pink);
        rf.addEventChannel(VisualizerProperties.ROOT_COLOR_ENABLED_ECD,
        	Boolean.TRUE);
        rf.addEventChannel(VisualizerProperties.SYNCHRONIZER_COLOR_ECD, Color.blue);
        rf.addEventChannel(VisualizerProperties.SYNCHRONIZER_COLOR_ENABLED_ECD,
        	Boolean.TRUE);
        rf.addEventChannel(VisualizerProperties.TRIGGEREDCOMMIT_COLOR_ECD, Color.cyan);
        rf.addEventChannel(VisualizerProperties.TRIGGEREDCOMMIT_COLOR_ENABLED_ECD,
        	Boolean.TRUE);
        rf.addEventChannel(VisualizerProperties.TRIGGEREDCONVERTER_COLOR_ECD, Color.green);
        rf.addEventChannel(VisualizerProperties.TRIGGEREDCONVERTER_COLOR_ENABLED_ECD,
        	Boolean.TRUE);
        rf.addEventChannel(VisualizerProperties.VALIDATOR_COLOR_ECD, Color.orange);
        rf.addEventChannel(VisualizerProperties.VALIDATOR_COLOR_ENABLED_ECD,
        	Boolean.TRUE);
        
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