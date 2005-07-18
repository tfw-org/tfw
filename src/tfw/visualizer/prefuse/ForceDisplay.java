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
package tfw.visualizer.prefuse;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.HashMap;

import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.TreeComponent;
import edu.berkeley.guir.prefuse.Display;
import edu.berkeley.guir.prefuse.EdgeItem;
import edu.berkeley.guir.prefuse.ItemRegistry;
import edu.berkeley.guir.prefuse.NodeItem;
import edu.berkeley.guir.prefuse.VisualItem;
import edu.berkeley.guir.prefuse.action.RepaintAction;
import edu.berkeley.guir.prefuse.action.assignment.ColorFunction;
import edu.berkeley.guir.prefuse.action.filter.GraphFilter;
import edu.berkeley.guir.prefuse.activity.ActionList;
import edu.berkeley.guir.prefuse.graph.DefaultGraph;
import edu.berkeley.guir.prefuse.graph.DefaultTreeNode;
import edu.berkeley.guir.prefuse.render.DefaultEdgeRenderer;
import edu.berkeley.guir.prefuse.render.DefaultRendererFactory;
import edu.berkeley.guir.prefuse.render.TextItemRenderer;
import edu.berkeley.guir.prefusex.controls.DragControl;
import edu.berkeley.guir.prefusex.controls.FocusControl;
import edu.berkeley.guir.prefusex.controls.NeighborHighlightControl;
import edu.berkeley.guir.prefusex.controls.PanControl;
import edu.berkeley.guir.prefusex.controls.ZoomControl;
import edu.berkeley.guir.prefusex.force.DragForce;
import edu.berkeley.guir.prefusex.force.ForceSimulator;
import edu.berkeley.guir.prefusex.force.NBodyForce;
import edu.berkeley.guir.prefusex.force.SpringForce;
import edu.berkeley.guir.prefusex.layout.ForceDirectedLayout;

public class ForceDisplay extends Display implements BranchBox
{
    private final Branch branch;
    
    public ForceDisplay(String name, TreeComponent treeComponent)
    {
        branch = new Branch("ForceDisplay["+name+"]");
        
        DefaultTreeNode rootNode = new DefaultTreeNode();
        rootNode.setAttribute("label", treeComponent.getName());
        ArrayList nodes = new ArrayList();
        HashMap proxies = new HashMap();
        nodes.add(rootNode);
        PrefuseUtil.createNodes(treeComponent, rootNode, nodes, proxies);
        ItemRegistry registry = new ItemRegistry(
        	new DefaultGraph(nodes, false));

        ForceSimulator fsim = new ForceSimulator();
        fsim.addForce(new NBodyForce(-0.4f, -1f, 0.9f));
        fsim.addForce(new SpringForce(4E-5f, 75f));
        fsim.addForce(new DragForce(-0.005f));

        setItemRegistry(registry);
        
        TextItemRenderer nodeRenderer = new TextItemRenderer();
        nodeRenderer.setRenderType(TextItemRenderer.RENDER_TYPE_FILL);
        nodeRenderer.setRoundedCorner(8,8);
        nodeRenderer.setTextAttributeName("label");
        DefaultEdgeRenderer edgeRenderer = new DefaultEdgeRenderer();    
        m_registry.setRendererFactory(new DefaultRendererFactory(
                nodeRenderer, edgeRenderer, null));
        
        ActionList actionList = new ActionList(m_registry,-1,20);
        actionList.add(new GraphFilter());
        actionList.add(new ForceDirectedLayout(fsim, false, false));
        actionList.add(new DemoColorFunction());
        actionList.add(new RepaintAction());
        
        addControlListener(new NeighborHighlightControl());
        addControlListener(new DragControl(false, true));
        addControlListener(new FocusControl(0));
        addControlListener(new PanControl(false));
        addControlListener(new ZoomControl(false));

        actionList.runNow();
    }
    
    private class DemoColorFunction extends ColorFunction
	{
        private Color pastelRed = new Color(255,125,125);
        private Color pastelOrange = new Color(255,200,125);
        private Color lightGray = new Color(220,220,255);
        public Paint getColor(VisualItem item)
        {
            if ( item instanceof EdgeItem )
            {
                if ( item.isHighlighted() )
                    return pastelOrange;
                else
                    return Color.LIGHT_GRAY;
            }
            else
            {
                return Color.BLACK;
            }
        }
        public Paint getFillColor(VisualItem item)
        {
            if ( item.isHighlighted() )
                return pastelOrange;
            else if ( item instanceof NodeItem )
            {
                if ( item.isFocus() )
                    return pastelRed;
                else
                    return lightGray;
            }
            else
            {
                return Color.BLACK;
            }
        }  
    }
    
    public final Branch getBranch()
    {
        return(branch);
    }
}
