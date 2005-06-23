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
    private static final BooleanECD EXIT_ENABLED_ECD =
        new BooleanECD("exitEnabled");
    private static final StatelessTriggerECD EXIT_TRIGGER_ECD =
        new StatelessTriggerECD("exitTrigger");
    private static final BooleanECD FORCE_ENABLED_ECD =
    	new BooleanECD("forceEnabled");
    private static final StatelessTriggerECD FORCE_TRIGGER_ECD =
    	new StatelessTriggerECD("forceTrigger");
    private static final BooleanECD RADIAL_GRAPH_ENABLED_ECD =
    	new BooleanECD("radialGraphEnabled");
    private static final StatelessTriggerECD RADIAL_GRAPH_TRIGGER_ECD =
    	new StatelessTriggerECD("radialGraphTrigger");
    private static final BooleanECD TREE_MAP_ENABLED_ECD =
    	new BooleanECD("treeMapEnabled");
    private static final StatelessTriggerECD TREE_MAP_TRIGGER_ECD =
    	new StatelessTriggerECD("treeMapTrigger");
    
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
        
        JMenuBarBB menuBar = new JMenuBarBB("Visualizer");
        menuBar.addToBoth(fileM);
        menuBar.addToBoth(viewM);
        
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
        rf.addTerminator(EXIT_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(EXIT_TRIGGER_ECD);
        rf.addTerminator(FORCE_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(FORCE_TRIGGER_ECD);
        rf.addTerminator(RADIAL_GRAPH_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(RADIAL_GRAPH_TRIGGER_ECD);
        rf.addTerminator(TREE_MAP_ENABLED_ECD, Boolean.TRUE);
        rf.addTerminator(TREE_MAP_TRIGGER_ECD);
        
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