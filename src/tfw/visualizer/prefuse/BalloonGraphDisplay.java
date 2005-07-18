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
import edu.berkeley.guir.prefuse.AggregateItem;
import edu.berkeley.guir.prefuse.Display;
import edu.berkeley.guir.prefuse.EdgeItem;
import edu.berkeley.guir.prefuse.ItemRegistry;
import edu.berkeley.guir.prefuse.NodeItem;
import edu.berkeley.guir.prefuse.VisualItem;
import edu.berkeley.guir.prefuse.action.RepaintAction;
import edu.berkeley.guir.prefuse.action.animate.ColorAnimator;
import edu.berkeley.guir.prefuse.action.animate.LocationAnimator;
import edu.berkeley.guir.prefuse.action.assignment.ColorFunction;
import edu.berkeley.guir.prefuse.action.filter.WindowedTreeFilter;
import edu.berkeley.guir.prefuse.activity.ActionList;
import edu.berkeley.guir.prefuse.activity.SlowInSlowOutPacer;
import edu.berkeley.guir.prefuse.collections.DOIItemComparator;
import edu.berkeley.guir.prefuse.graph.DefaultGraph;
import edu.berkeley.guir.prefuse.graph.DefaultTreeNode;
import edu.berkeley.guir.prefuse.render.DefaultEdgeRenderer;
import edu.berkeley.guir.prefuse.render.DefaultNodeRenderer;
import edu.berkeley.guir.prefuse.render.Renderer;
import edu.berkeley.guir.prefuse.render.RendererFactory;
import edu.berkeley.guir.prefuse.render.TextItemRenderer;
import edu.berkeley.guir.prefuse.util.ColorMap;
import edu.berkeley.guir.prefuse.util.StringAbbreviator;
import edu.berkeley.guir.prefusex.controls.FocusControl;
import edu.berkeley.guir.prefusex.controls.NeighborHighlightControl;
import edu.berkeley.guir.prefusex.controls.PanControl;
import edu.berkeley.guir.prefusex.controls.SubtreeDragControl;
import edu.berkeley.guir.prefusex.controls.ZoomControl;
import edu.berkeley.guir.prefusex.layout.BalloonTreeLayout;

public class BalloonGraphDisplay extends Display implements BranchBox
{
    private final Branch branch;
    
    public BalloonGraphDisplay(String name, TreeComponent treeComponent)
    {
        branch = new Branch("BalloonGraphDisplay["+name+"]");
        
        DefaultTreeNode rootNode = new DefaultTreeNode();
        rootNode.setAttribute("label", treeComponent.getName());
        ArrayList nodes = new ArrayList();
        HashMap proxies = new HashMap();
        nodes.add(rootNode);
        PrefuseUtil.createNodes(treeComponent, rootNode, nodes, proxies);
        ItemRegistry registry = new ItemRegistry(
        	new DefaultGraph(nodes, false));
        registry.setItemComparator(new DOIItemComparator());

        setItemRegistry(registry);

        TextItemRenderer nodeRenderer = new TextItemRenderer();
		nodeRenderer.setMaxTextWidth(75);
		nodeRenderer.setAbbrevType(StringAbbreviator.NAME);
        nodeRenderer.setRoundedCorner(8,8);
		
		Renderer nodeRenderer2 = new DefaultNodeRenderer();
		Renderer edgeRenderer = new DefaultEdgeRenderer();
		
		registry.setRendererFactory(new DemoRendererFactory(
			nodeRenderer, nodeRenderer2, edgeRenderer));

		ActionList filter = new ActionList(registry);
        filter.add(new WindowedTreeFilter(-4,true));
        filter.add(new BalloonTreeLayout());
        filter.add(new DemoColorFunction(4));
        
        ActionList update = new ActionList(registry);
        update.add(new DemoColorFunction(4));
        update.add(new RepaintAction());
        
        ActionList animate = new ActionList(registry, 1500, 20);
        animate.setPacingFunction(new SlowInSlowOutPacer());
        animate.add(new LocationAnimator());
        animate.add(new ColorAnimator());
        animate.add(new RepaintAction());
        animate.alwaysRunAfter(filter);
        
        addControlListener(new FocusControl(filter));
        addControlListener(new SubtreeDragControl());
        addControlListener(new PanControl());
        addControlListener(new ZoomControl());
        addControlListener(new NeighborHighlightControl(update));

        filter.runNow();
    }
    
    public class DemoRendererFactory implements RendererFactory
	{
        private Renderer nodeRenderer1;
        private Renderer nodeRenderer2;
        private Renderer edgeRenderer;
        public DemoRendererFactory(Renderer nr1, Renderer nr2, Renderer er)
        {
            nodeRenderer1 = nr1;
            nodeRenderer2 = nr2;
            edgeRenderer = er;
        }
        
        public Renderer getRenderer(VisualItem item)
        {
            if ( item instanceof NodeItem )
            {
                int d = ((NodeItem)item).getDepth();
                if ( d > 1 )
                {
                    int r = (d == 2 ? 5 : 1);
                    ((DefaultNodeRenderer)nodeRenderer2).setRadius(r);
                    return nodeRenderer2;
                }
                else
                {
                    return nodeRenderer1;
                }
            }
            else if ( item instanceof EdgeItem )
            {
                return edgeRenderer;
            }
            else
            {
                return null;
            }
        }
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
