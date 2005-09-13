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

import tfw.tsm.Branch;
import tfw.tsm.MultiplexedBranch;
import tfw.tsm.MultiplexedBranchFactory;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ila.BooleanIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;
import tfw.tsm.ecd.ilm.IntIlmECD;

public class VisualizerToolBarController extends Branch
{
	public static final BooleanECD BUTTON_ONE_ECD =
		new BooleanECD("buttonOne");
	public static final BooleanECD BUTTON_TWO_ECD =
		new BooleanECD("buttonTwo");
	public static final BooleanECD BUTTON_THREE_ECD =
		new BooleanECD("buttonThree");
	public static final IntegerECD GRAPH_HEIGHT_ECD =
		new IntegerECD("graphHeight");
	public static final IntegerECD GRAPH_WIDTH_ECD =
		new IntegerECD("graphWidth");
	public static final ObjectIlaECD MULTI_TOOL_SELECTED_ECD =
		new ObjectIlaECD("multiToolSelected");
	public static final IntIlmECD PIXEL_NODE_TLBR_ECD =
		new IntIlmECD("pixelNodeTLBR");
	public static final BooleanIlaECD SELECTED_NODES_ECD =
		new BooleanIlaECD("selectedNodes");
	public static final BooleanECD TOOL_SELECTED_ECD =
		new BooleanECD("toolSelected");
	public static final IntegerECD X_MOUSE_ECD =
		new IntegerECD("xMouse");
	public static final IntegerECD X_OFFSET_ECD =
		new IntegerECD("xOffset");
	public static final IntegerECD Y_MOUSE_ECD =
		new IntegerECD("yMouse");
	public static final IntegerECD Y_OFFSET_ECD =
		new IntegerECD("yOffset");
	
	public VisualizerToolBarController()
	{
		super("VisualizerToolBarController");
		
		MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
		mbf.addMultiplexer(TOOL_SELECTED_ECD, MULTI_TOOL_SELECTED_ECD);
		MultiplexedBranch mb = mbf.create("MultiplexedBranch");
		add(mb);
		
		mb.add(new SelectionConverter(TOOL_SELECTED_ECD, BUTTON_ONE_ECD,
			BUTTON_TWO_ECD, BUTTON_THREE_ECD, PIXEL_NODE_TLBR_ECD, X_MOUSE_ECD,
			Y_MOUSE_ECD, SELECTED_NODES_ECD), 0);
		
        mb.add(new MovePlotConverter(TOOL_SELECTED_ECD, X_MOUSE_ECD,
        	Y_MOUSE_ECD, BUTTON_ONE_ECD, BUTTON_TWO_ECD, BUTTON_THREE_ECD,
            X_OFFSET_ECD, Y_OFFSET_ECD), 1);
        mb.add(new ResizePlotConverter(TOOL_SELECTED_ECD, X_MOUSE_ECD,
        	Y_MOUSE_ECD, BUTTON_ONE_ECD, BUTTON_TWO_ECD, BUTTON_THREE_ECD,
            GRAPH_WIDTH_ECD, GRAPH_HEIGHT_ECD), 1);
	}
}