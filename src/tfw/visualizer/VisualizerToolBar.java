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

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import tfw.swing.JPanelBB;
import tfw.swing.JToggleButtonBB;
import tfw.tsm.MultiplexedBranch;
import tfw.tsm.MultiplexedBranchFactory;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class VisualizerToolBar extends JPanelBB
{
	public static final BooleanECD MOVE_ENABLED_ECD =
		new BooleanECD("moveEnabled");
	public static final ObjectIlaECD MULTI_TOOL_SELECTED_ECD =
		new ObjectIlaECD("multiToolSelected");
	public static final BooleanECD SELECTION_ENABLED_ECD =
		new BooleanECD("selectionEnabled");
	public static final BooleanECD TOOL_SELECTED_ECD =
		new BooleanECD("toolSelected");
	
	public VisualizerToolBar()
	{
		super("VisualizerToolBar");
		
		MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
		mbf.addMultiplexer(TOOL_SELECTED_ECD, MULTI_TOOL_SELECTED_ECD);
		MultiplexedBranch mb = mbf.create("Buttons");
		
		JToggleButtonBB selectionTB = new JToggleButtonBB("Selection",
			SELECTION_ENABLED_ECD, TOOL_SELECTED_ECD);
		selectionTB.setText("Sele");
		JToggleButtonBB moveTB = new JToggleButtonBB("Move",
			MOVE_ENABLED_ECD, TOOL_SELECTED_ECD);
		moveTB.setText("Move");
		
		// Use a commit/initiator pair instead.
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(selectionTB);
		buttonGroup.add(moveTB);
		buttonGroup.setSelected(selectionTB.getModel(), true);
		
		// Add a method on MultiplexedBranch for add(BranchBox, index).
		mb.add(selectionTB.getBranch(), 0);
		mb.add(moveTB.getBranch(), 1);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(selectionTB);
		add(moveTB);
	}
}