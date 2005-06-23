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

import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.TreeComponent;
import edu.berkeley.guir.prefuse.AggregateItem;
import edu.berkeley.guir.prefuse.Display;
import edu.berkeley.guir.prefuse.EdgeItem;
import edu.berkeley.guir.prefuse.FocusManager;
import edu.berkeley.guir.prefuse.ItemRegistry;
import edu.berkeley.guir.prefuse.NodeItem;
import edu.berkeley.guir.prefuse.VisualItem;
import edu.berkeley.guir.prefuse.action.RepaintAction;
import edu.berkeley.guir.prefuse.action.animate.ColorAnimator;
import edu.berkeley.guir.prefuse.action.animate.PolarLocationAnimator;
import edu.berkeley.guir.prefuse.action.assignment.ColorFunction;
import edu.berkeley.guir.prefuse.action.filter.TreeFilter;
import edu.berkeley.guir.prefuse.activity.ActionList;
import edu.berkeley.guir.prefuse.activity.SlowInSlowOutPacer;
import edu.berkeley.guir.prefuse.collections.DOIItemComparator;
import edu.berkeley.guir.prefuse.focus.DefaultFocusSet;
import edu.berkeley.guir.prefuse.graph.DefaultGraph;
import edu.berkeley.guir.prefuse.graph.DefaultTreeNode;
import edu.berkeley.guir.prefuse.render.DefaultEdgeRenderer;
import edu.berkeley.guir.prefuse.render.DefaultRendererFactory;
import edu.berkeley.guir.prefuse.render.Renderer;
import edu.berkeley.guir.prefuse.render.TextItemRenderer;
import edu.berkeley.guir.prefuse.util.ColorMap;
import edu.berkeley.guir.prefuse.util.StringAbbreviator;
import edu.berkeley.guir.prefusex.controls.DragControl;
import edu.berkeley.guir.prefusex.controls.FocusControl;
import edu.berkeley.guir.prefusex.controls.NeighborHighlightControl;
import edu.berkeley.guir.prefusex.controls.PanControl;
import edu.berkeley.guir.prefusex.controls.ZoomControl;
import edu.berkeley.guir.prefusex.layout.RadialTreeLayout;

public class RadialGraphDisplay extends Display implements BranchBox
{
    private final Branch branch;
    
    public RadialGraphDisplay(String name, TreeComponent treeComponent)
    {
        branch = new Branch("RadialGraphDisplay["+name+"]");
        
        DefaultTreeNode rootNode = new DefaultTreeNode();
        rootNode.setAttribute("label", treeComponent.getName());
        ArrayList nodes = new ArrayList();
        nodes.add(rootNode);
        PrefuseUtil.createNodes(treeComponent, rootNode, nodes);
        ItemRegistry registry = new ItemRegistry(
        	new DefaultGraph(nodes, false));
        registry.setItemComparator(new DOIItemComparator());

        setItemRegistry(registry);

		TextItemRenderer nodeRenderer = new TextItemRenderer();
		nodeRenderer.setMaxTextWidth(150);
		nodeRenderer.setAbbrevType(StringAbbreviator.NAME);
        nodeRenderer.setRoundedCorner(8,8);
		
		Renderer edgeRenderer = new DefaultEdgeRenderer();
		
		registry.setRendererFactory(new DefaultRendererFactory(
			nodeRenderer, edgeRenderer));

		ActionList layout = new ActionList(registry);
        layout.add(new TreeFilter(true));
        layout.add(new RadialTreeLayout());
        layout.add(new DemoColorFunction(3));
        
        ActionList update = new ActionList(registry);
        update.add(new DemoColorFunction(3));
        update.add(new RepaintAction());
        
        ActionList animate = new ActionList(registry, 1500, 20);
        animate.setPacingFunction(new SlowInSlowOutPacer());
        animate.add(new PolarLocationAnimator());
        animate.add(new ColorAnimator());
        animate.add(new RepaintAction());
        animate.alwaysRunAfter(layout);

        addControlListener(new FocusControl(layout));
        addControlListener(new FocusControl(0,FocusManager.HOVER_KEY));
        addControlListener(new DragControl());
        addControlListener(new PanControl());
        addControlListener(new ZoomControl());
        addControlListener(new NeighborHighlightControl(update));
        
        registry.getFocusManager().putFocusSet(
                FocusManager.HOVER_KEY, new DefaultFocusSet());

        layout.runNow();
    }

    public class DemoColorFunction extends ColorFunction
	{
        private Color graphEdgeColor = Color.LIGHT_GRAY;
        private Color highlightColor = Color.BLUE;
        private ColorMap cmap; 
        
        public DemoColorFunction(int thresh)
        {
            cmap = new ColorMap(
                ColorMap.getInterpolatedMap(Color.RED, Color.BLACK),0,thresh);
        }
        
        public Paint getFillColor(VisualItem item)
        {
            if ( item instanceof NodeItem )
            {
                return Color.WHITE;
            }
            else if ( item instanceof AggregateItem )
            {
                return Color.LIGHT_GRAY;
            }
            else if ( item instanceof EdgeItem )
            {
                return getColor(item);
            }
            else
            {
                return Color.BLACK;
            }
        }
        
        public Paint getColor(VisualItem item)
        {
            if ( item.isHighlighted() )
            {
                return Color.BLUE;
            }
            else if (item instanceof NodeItem)
            {
                int d = ((NodeItem)item).getDepth();
                return cmap.getColor(d);
            }
            else if (item instanceof EdgeItem)
            {
                EdgeItem e = (EdgeItem) item;
                if ( e.isTreeEdge() )
                {
                    int d, d1, d2;
                    d1 = ((NodeItem)e.getFirstNode()).getDepth();
                    d2 = ((NodeItem)e.getSecondNode()).getDepth();
                    d = Math.max(d1, d2);
                    return cmap.getColor(d);
                }
                else
                {
                    return graphEdgeColor;
                }
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
