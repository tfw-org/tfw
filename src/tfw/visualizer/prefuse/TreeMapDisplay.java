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
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.TreeComponent;
import edu.berkeley.guir.prefuse.Display;
import edu.berkeley.guir.prefuse.ItemRegistry;
import edu.berkeley.guir.prefuse.NodeItem;
import edu.berkeley.guir.prefuse.VisualItem;
import edu.berkeley.guir.prefuse.action.AbstractAction;
import edu.berkeley.guir.prefuse.action.RepaintAction;
import edu.berkeley.guir.prefuse.action.assignment.ColorFunction;
import edu.berkeley.guir.prefuse.action.filter.TreeFilter;
import edu.berkeley.guir.prefuse.activity.ActionList;
import edu.berkeley.guir.prefuse.graph.DefaultGraph;
import edu.berkeley.guir.prefuse.graph.DefaultTreeNode;
import edu.berkeley.guir.prefuse.render.DefaultRendererFactory;
import edu.berkeley.guir.prefuse.render.ShapeRenderer;
import edu.berkeley.guir.prefuse.util.ColorMap;
import edu.berkeley.guir.prefusex.controls.PanControl;
import edu.berkeley.guir.prefusex.controls.ToolTipControl;
import edu.berkeley.guir.prefusex.controls.ZoomControl;
import edu.berkeley.guir.prefusex.layout.SquarifiedTreeMapLayout;

public class TreeMapDisplay extends Display implements BranchBox
{
    private final Branch branch;
    
    public TreeMapDisplay(String name, TreeComponent treeComponent)
    {
        branch = new Branch("TreeMapDisplay["+name+"]");
        
        DefaultTreeNode rootNode = new DefaultTreeNode();
        rootNode.setAttribute("label", treeComponent.getName());
        ArrayList nodes = new ArrayList();
        HashMap proxies = new HashMap();
        nodes.add(rootNode);
        PrefuseUtil.createNodes(treeComponent, rootNode, nodes, proxies);
        ItemRegistry registry = new ItemRegistry(
        	new DefaultGraph(nodes, false));
        
        registry.setRendererFactory(new DefaultRendererFactory(
            new RectangleRenderer()));
        registry.setItemComparator(new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                double s1 = ((VisualItem)o1).getSize();
                double s2 = ((VisualItem)o2).getSize();
                return ( s1>s2 ? -1 : (s1<s2 ? 1 : 0));
            }
        });
        
        ActionList filter = new ActionList(registry);
        filter.add(new TreeFilter(false, false));
        filter.add(new TreeMapSizeFunction());
        filter.add(new SquarifiedTreeMapLayout(4));
        filter.add(new TreeMapColorFunction());
        filter.add(new RepaintAction());
        
        PanControl  pH = new PanControl();
        ZoomControl zH = new ZoomControl();
        
        addMouseListener(pH);
        addMouseMotionListener(pH);
        addMouseListener(zH);
        addMouseMotionListener(zH);
        addControlListener(new ToolTipControl());
        setItemRegistry(registry);
        setUseCustomTooltips(true);
        filter.runNow();
    }
    
    private static class TreeMapColorFunction extends ColorFunction
    {
        Color c1 = new Color(0.5f,0.5f,0.f);
        Color c2 = new Color(0.5f,0.5f,1.f);
        ColorMap cmap = new ColorMap(ColorMap.getInterpolatedMap(10,c1,c2),0,9);
        public Paint getColor(VisualItem item)
        {
            return Color.WHITE;
        }
        public Paint getFillColor(VisualItem item)
        {
            double v = (item instanceof NodeItem ? ((NodeItem)item).getDepth():0);
            return cmap.getColor(v);
        }
    }
    
    private static class TreeMapSizeFunction extends AbstractAction
    {
        public void run(ItemRegistry registry, double frac)
        {
            int leafCount = 0;
            Iterator iter = registry.getNodeItems();
            while ( iter.hasNext() )
            {
                NodeItem n = (NodeItem)iter.next();
                if ( n.getChildCount() == 0 )
                {
                    n.setSize(1.0);
                    NodeItem p = (NodeItem)n.getParent();
                    for (; p!=null; p=(NodeItem)p.getParent())
                        p.setSize(1.0+p.getSize());
                    leafCount++;
                }
            }
            
            Dimension d = registry.getDisplay(0).getSize();
            double area = d.width*d.height;
            double divisor = ((double)leafCount)/area;
            iter = registry.getNodeItems();
            while ( iter.hasNext() )
            {
                NodeItem n = (NodeItem)iter.next();
                n.setSize(n.getSize()/divisor);
            }
        }
    }

    private static class RectangleRenderer extends ShapeRenderer
    {
        private Rectangle2D bounds = new Rectangle2D.Double();
        
        protected Shape getRawShape(VisualItem item)
        {
            Point2D d = (Point2D)item.getVizAttribute("dimension");
            if (d == null)
                System.out.println("uh-oh");
            bounds.setRect(item.getX(),item.getY(),d.getX(),d.getY());
            return bounds;
        }
    }
    
    public final Branch getBranch()
    {
        return(branch);
    }
}
