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

import tfw.awt.event.WindowInitiator;
import tfw.swing.JCheckBoxMenuItemBB;
import tfw.swing.JFrameBB;
import tfw.swing.JMenuBB;
import tfw.swing.JMenuBarBB;
import tfw.swing.JMenuItemBB;
import tfw.tsm.AWTTransactionQueue;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TreeComponent;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.visualizer.prefuse.BalloonGraphDisplayCommit;
import tfw.visualizer.prefuse.ForceDisplayCommit;
import tfw.visualizer.prefuse.RadialGraphDisplayCommit;
import tfw.visualizer.prefuse.TreeMapDisplayCommit;

public class Visualizer extends JFrameBB
{
	private static final BooleanECD BALLOON_GRAPH_ENABLED_ECD =
		new BooleanECD("balloonGraphEnabled");
	private static final StatelessTriggerECD BALLOON_GRAPH_TRIGGER_ECD =
		new StatelessTriggerECD("balloonGraphTrigger");
	private static final BooleanECD BRANCH_ENABLED_ECD =
		new BooleanECD("branchEnabled");
	private static final BooleanECD BRANCH_SELECTED_ECD =
		new BooleanECD("branchSelected");
	private static final BooleanECD COMMIT_ENABLED_ECD =
		new BooleanECD("commitEnabled");
	private static final BooleanECD COMMIT_SELECTED_ECD =
		new BooleanECD("commitSelected");
	private static final BooleanECD CONVERTER_ENABLED_ECD =
		new BooleanECD("converterEnabled");
	private static final BooleanECD CONVERTER_SELECTED_ECD =
		new BooleanECD("converterSelected");
    private static final BooleanECD EXIT_ENABLED_ECD =
        new BooleanECD("exitEnabled");
    private static final StatelessTriggerECD EXIT_TRIGGER_ECD =
        new StatelessTriggerECD("exitTrigger");
    private static final BooleanECD FORCE_ENABLED_ECD =
    	new BooleanECD("forceEnabled");
    private static final StatelessTriggerECD FORCE_TRIGGER_ECD =
    	new StatelessTriggerECD("forceTrigger");
    private static final BooleanECD INITIATOR_ENABLED_ECD =
    	new BooleanECD("initiatorEnabled");
    private static final BooleanECD INITIATOR_SELECTED_ECD =
    	new BooleanECD("initiatorSelected");
    private static final BooleanECD RADIAL_GRAPH_ENABLED_ECD =
    	new BooleanECD("radialGraphEnabled");
    private static final StatelessTriggerECD RADIAL_GRAPH_TRIGGER_ECD =
    	new StatelessTriggerECD("radialGraphTrigger");
    private static final BooleanECD ROOT_ENABLED_ECD =
    	new BooleanECD("rootEnabled");
    private static final BooleanECD ROOT_SELECTED_ECD =
    	new BooleanECD("rootSelected");
    private static final BooleanECD SYNCHRONIZER_ENABLED_ECD =
    	new BooleanECD("synchronizerEnabled");
    private static final BooleanECD SYNCHRONIZER_SELECTED_ECD =
    	new BooleanECD("synchronizerSelected");
    private static final BooleanECD TERMINATOR_ENABLED_ECD =
    	new BooleanECD("terminatorEnabled");
    private static final BooleanECD TERMINATOR_SELECTED_ECD =
    	new BooleanECD("terminatorSelected");
    private static final BooleanECD TREE_MAP_ENABLED_ECD =
    	new BooleanECD("treeMapEnabled");
    private static final StatelessTriggerECD TREE_MAP_TRIGGER_ECD =
    	new StatelessTriggerECD("treeMapTrigger");
    private static final BooleanECD TRIGGERED_COMMIT_ENABLED_ECD =
    	new BooleanECD("triggeredCommitEnabled");
    private static final BooleanECD TRIGGERED_COMMIT_SELECTED_ECD =
    	new BooleanECD("triggeredCommitSelected");
    private static final BooleanECD TRIGGERED_CONVERTER_ENABLED_ECD =
    	new BooleanECD("triggeredConverterEnabled");
    private static final BooleanECD TRIGGERED_CONVERTER_SELECTED_ECD =
    	new BooleanECD("triggeredConverterSelected");
    private static final BooleanECD VALIDATOR_ENABLED_ECD =
    	new BooleanECD("validatorEnabled");
    private static final BooleanECD VALIDATOR_SELECTED_ECD =
    	new BooleanECD("validatorSelected");
    
    public Visualizer(TreeComponent treeComponent)
    {
        super(createRoot("Visualizer["+treeComponent.getName()+"]"));
        
        JMenuItemBB exitMI = new JMenuItemBB("Exit", EXIT_TRIGGER_ECD,
            EXIT_ENABLED_ECD);
        exitMI.setText("Exit");
        
        JMenuBB fileM = new JMenuBB("File");
        fileM.setText("File");
        fileM.addToBoth(exitMI);
        
        JMenuItemBB balloonGraphMI = new JMenuItemBB("BalloonGrph",
			BALLOON_GRAPH_TRIGGER_ECD, BALLOON_GRAPH_ENABLED_ECD);
        balloonGraphMI.setText("Balloon Graph");
        JMenuItemBB forceMI = new JMenuItemBB("Force", FORCE_TRIGGER_ECD,
        	FORCE_ENABLED_ECD);
        forceMI.setText("Force");
        JMenuItemBB radialGraphMI = new JMenuItemBB("RadialGraph",
        	RADIAL_GRAPH_TRIGGER_ECD, RADIAL_GRAPH_ENABLED_ECD);
        radialGraphMI.setText("Radial Graph");
        JMenuItemBB treeMapMI = new JMenuItemBB("TreeMap", TREE_MAP_TRIGGER_ECD,
            	TREE_MAP_ENABLED_ECD);
            treeMapMI.setText("TreeMap");
       
        JMenuBB viewM = new JMenuBB("View");
        viewM.setText("View");
        viewM.addToBoth(balloonGraphMI);
        viewM.addToBoth(forceMI);
        viewM.addToBoth(radialGraphMI);
        viewM.addToBoth(treeMapMI);
        
        JCheckBoxMenuItemBB showBranchMI = new JCheckBoxMenuItemBB(
        	"ShowBranch", BRANCH_SELECTED_ECD, BRANCH_ENABLED_ECD);
        showBranchMI.setText("Branch");
        JCheckBoxMenuItemBB showCommitMI = new JCheckBoxMenuItemBB(
        	"showCommit", COMMIT_SELECTED_ECD, COMMIT_ENABLED_ECD);
        showCommitMI.setText("Commit");
        JCheckBoxMenuItemBB showConverterMI = new JCheckBoxMenuItemBB(
        	"showConverter", CONVERTER_SELECTED_ECD, CONVERTER_ENABLED_ECD);
        showConverterMI.setText("Converter");
        JCheckBoxMenuItemBB showInitiatorMI = new JCheckBoxMenuItemBB(
        	"showInitiator", INITIATOR_SELECTED_ECD, INITIATOR_ENABLED_ECD);
        showInitiatorMI.setText("Initiator");
        JCheckBoxMenuItemBB showRootMI = new JCheckBoxMenuItemBB(
        	"showRoot", ROOT_SELECTED_ECD, ROOT_ENABLED_ECD);
        showRootMI.setText("Root");
        JCheckBoxMenuItemBB showSynchronizerMI = new JCheckBoxMenuItemBB(
        	"showSynchronizer", SYNCHRONIZER_SELECTED_ECD,
			SYNCHRONIZER_ENABLED_ECD);
        showSynchronizerMI.setText("Synchronizer");
        JCheckBoxMenuItemBB showTerminatorMI = new JCheckBoxMenuItemBB(
        	"showTerminator", TERMINATOR_ENABLED_ECD, TERMINATOR_SELECTED_ECD);
        showTerminatorMI.setText("Terminator");
        JCheckBoxMenuItemBB showTriggeredCommitMI = new JCheckBoxMenuItemBB(
        	"showTriggeredCommit", TRIGGERED_COMMIT_SELECTED_ECD,
			TRIGGERED_COMMIT_ENABLED_ECD);
        showTriggeredCommitMI.setText("TriggeredCommit");
        JCheckBoxMenuItemBB showTriggeredConverterMI = new JCheckBoxMenuItemBB(
        	"showTriggeredConverter", TRIGGERED_CONVERTER_SELECTED_ECD,
			TRIGGERED_CONVERTER_ENABLED_ECD);
        showTriggeredConverterMI.setText("TriggeredConverter");
        JCheckBoxMenuItemBB showValidatorMI = new JCheckBoxMenuItemBB(
        	"showValidator", VALIDATOR_SELECTED_ECD, VALIDATOR_ENABLED_ECD);
        showValidatorMI.setText("Validator");
        
        JMenuBB showM = new JMenuBB("Show");
        showM.setText("Show");
        showM.addToBoth(showBranchMI);
        showM.addToBoth(showCommitMI);
        showM.addToBoth(showConverterMI);
        showM.addToBoth(showInitiatorMI);
        showM.addToBoth(showRootMI);
        showM.addToBoth(showSynchronizerMI);
        showM.addToBoth(showTerminatorMI);
        showM.addToBoth(showTriggeredCommitMI);
        showM.addToBoth(showTriggeredConverterMI);
        showM.addToBoth(showValidatorMI);
        
        JMenuBarBB menuBar = new JMenuBarBB("Visualizer");
        menuBar.addToBoth(fileM);
        menuBar.addToBoth(viewM);
        menuBar.addToBoth(showM);
        
        setJMenuBarForBoth(menuBar);
        
        addWindowListenerToBoth(new WindowInitiator("Visualizer", null, null,
            EXIT_TRIGGER_ECD, null, null, null, null));
        
        getBranch().add(new ExitConverter(EXIT_TRIGGER_ECD));
        getBranch().add(new BalloonGraphDisplayCommit(
        	"Visualizer", BALLOON_GRAPH_TRIGGER_ECD, treeComponent, this));
        getBranch().add(new ForceDisplayCommit(
        	"Visualizer", FORCE_TRIGGER_ECD, treeComponent, this));
        getBranch().add(new RadialGraphDisplayCommit(
        	"Visualizer", RADIAL_GRAPH_TRIGGER_ECD, treeComponent, this));
        getBranch().add(new TreeMapDisplayCommit(
            "Visualizer", TREE_MAP_TRIGGER_ECD, treeComponent, this));
    }
    
    private static Root createRoot(String name)
    {
        RootFactory rf = new RootFactory();
        
        rf.addTerminator(BALLOON_GRAPH_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(BALLOON_GRAPH_TRIGGER_ECD);
        rf.addTerminator(BRANCH_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(BRANCH_SELECTED_ECD, Boolean.TRUE);
        rf.addTerminator(COMMIT_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(COMMIT_SELECTED_ECD, Boolean.TRUE);
        rf.addTerminator(CONVERTER_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(CONVERTER_SELECTED_ECD, Boolean.TRUE);
        rf.addTerminator(EXIT_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(EXIT_TRIGGER_ECD);
        rf.addTerminator(FORCE_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(FORCE_TRIGGER_ECD);
        rf.addTerminator(INITIATOR_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(INITIATOR_SELECTED_ECD, Boolean.TRUE);
        rf.addTerminator(RADIAL_GRAPH_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(RADIAL_GRAPH_TRIGGER_ECD);
        rf.addTerminator(ROOT_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(ROOT_SELECTED_ECD, Boolean.TRUE);
        rf.addTerminator(SYNCHRONIZER_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(SYNCHRONIZER_SELECTED_ECD, Boolean.TRUE);
        rf.addTerminator(TERMINATOR_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(TERMINATOR_SELECTED_ECD, Boolean.TRUE);
        rf.addTerminator(TREE_MAP_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(TREE_MAP_TRIGGER_ECD);
        rf.addTerminator(TRIGGERED_COMMIT_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(TRIGGERED_COMMIT_SELECTED_ECD, Boolean.TRUE);
        rf.addTerminator(TRIGGERED_CONVERTER_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(TRIGGERED_CONVERTER_SELECTED_ECD, Boolean.TRUE);
        rf.addTerminator(VALIDATOR_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(VALIDATOR_SELECTED_ECD, Boolean.TRUE);
        
        return(rf.create(name, new AWTTransactionQueue()));
    }
    
    public static void main(String[] args)
    {
        Visualizer v = new Visualizer(new Root("TestBranch"));
        
        Visualizer v2 = new Visualizer(v.getBranch());
        v2.setSize(500, 500);
        v2.setVisible(true);
    }
}