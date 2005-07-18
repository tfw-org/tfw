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
import tfw.tsm.Converter;
import tfw.tsm.TreeComponent;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescription;
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
//import edu.berkeley.guir.prefuse.util.StringAbbreviator;
import edu.berkeley.guir.prefusex.controls.DragControl;
import edu.berkeley.guir.prefusex.controls.FocusControl;
import edu.berkeley.guir.prefusex.controls.NeighborHighlightControl;
import edu.berkeley.guir.prefusex.controls.PanControl;
import edu.berkeley.guir.prefusex.controls.ZoomControl;
import edu.berkeley.guir.prefusex.layout.RadialTreeLayout;

public class RadialGraphDisplay extends Display implements BranchBox
{
    private final Branch branch;
    private final TFWTreeFilter tfwTreeFilter;
    
    public RadialGraphDisplay(String name, TreeComponent treeComponent)
    {
        branch = new Branch("RadialGraphDisplay["+name+"]");
        
        DefaultTreeNode rootNode = new DefaultTreeNode();
        rootNode.setAttribute("label", treeComponent.getName());
        rootNode.setAttribute("tfw_type", "root");
        ArrayList nodes = new ArrayList();
        HashMap proxies = new HashMap();
        nodes.add(rootNode);
        PrefuseUtil.createNodes(treeComponent, rootNode, nodes, proxies);
        ItemRegistry registry = new ItemRegistry(
        	new DefaultGraph(nodes, false));
        registry.setItemComparator(new DOIItemComparator());

        setItemRegistry(registry);

		TextItemRenderer nodeRenderer = new TextItemRenderer();
//		nodeRenderer.setMaxTextWidth(150);
//		nodeRenderer.setAbbrevType(StringAbbreviator.NAME);
        nodeRenderer.setRoundedCorner(8,8);
		
		Renderer edgeRenderer = new DefaultEdgeRenderer();
		
		registry.setRendererFactory(new DefaultRendererFactory(
			nodeRenderer, edgeRenderer));

		tfwTreeFilter = new TFWTreeFilter(true);
		ActionList layout = new ActionList(registry);
        layout.add(tfwTreeFilter);
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
        
        getBranch().add(new TFWTreeFilterConverter(
        	new BooleanECD("branchSelected"),
        	new BooleanECD("commitSelected"),
        	new BooleanECD("converterSelected"),
        	new BooleanECD("initiatorSelected"),
        	new BooleanECD("rootSelected"),
        	new BooleanECD("synchronizerSelected"),
			new BooleanECD("terminatorSelected"),
        	new BooleanECD("triggeredCommitSelected"),
        	new BooleanECD("triggeredConverterSelected"),
        	new BooleanECD("validatorSelected"),
			tfwTreeFilter, layout));

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
    
    private static class TFWTreeFilterConverter extends Converter
	{
    	private final BooleanECD branchSelectedECD;
    	private final BooleanECD commitSelectedECD;
    	private final BooleanECD converterSelectedECD;
    	private final BooleanECD initiatorSelectedECD;
    	private final BooleanECD rootSelectedECD;
    	private final BooleanECD synchronizerSelectedECD;
    	private final BooleanECD terminatorSelectedECD;
    	private final BooleanECD triggeredCommitSelectedECD;
    	private final BooleanECD triggeredConverterSelectedECD;
    	private final BooleanECD validatorSelectedECD;
    	private final TFWTreeFilter tfwTreeFilter;
    	private final ActionList actionList;
    	
    	public TFWTreeFilterConverter(BooleanECD branchSelectedECD,
    		BooleanECD commitSelectedECD, BooleanECD converterSelectedECD,
			BooleanECD initiatorSelectedECD, BooleanECD rootSelectedECD,
			BooleanECD synchronizerSelectedECD,
			BooleanECD terminatorSelectedECD,
			BooleanECD triggeredCommitSelectedECD,
			BooleanECD triggeredConverterSelectedECD,
			BooleanECD validatorSelectedECD, TFWTreeFilter tfwTreeFilter,
			ActionList actionList)
    	{
    		super("TFWTreeFilterConverter",
    			new EventChannelDescription[] {branchSelectedECD,
    				commitSelectedECD, converterSelectedECD,
					initiatorSelectedECD, rootSelectedECD,
					synchronizerSelectedECD, terminatorSelectedECD,
					triggeredCommitSelectedECD, triggeredConverterSelectedECD,
					validatorSelectedECD},
				null,
				null);
    		
    		this.branchSelectedECD = branchSelectedECD;
    		this.commitSelectedECD = commitSelectedECD;
    		this.converterSelectedECD = converterSelectedECD;
    		this.initiatorSelectedECD = initiatorSelectedECD;
    		this.rootSelectedECD = rootSelectedECD;
    		this.synchronizerSelectedECD = synchronizerSelectedECD;
    		this.terminatorSelectedECD = terminatorSelectedECD;
    		this.triggeredCommitSelectedECD = triggeredCommitSelectedECD;
    		this.triggeredConverterSelectedECD = triggeredConverterSelectedECD;
    		this.validatorSelectedECD = validatorSelectedECD;
    		this.tfwTreeFilter = tfwTreeFilter;
    		this.actionList = actionList;
    	}
    	
    	protected void convert()
    	{
    		tfwTreeFilter.setBranchSelected(
    			((Boolean)get(branchSelectedECD)).booleanValue());
    		tfwTreeFilter.setCommitSelected(
    			((Boolean)get(commitSelectedECD)).booleanValue());
    		tfwTreeFilter.setConverterSelected(
    			((Boolean)get(converterSelectedECD)).booleanValue());
    		tfwTreeFilter.setInitiatorSelected(
    			((Boolean)get(initiatorSelectedECD)).booleanValue());
    		tfwTreeFilter.setRootSelected(
    			((Boolean)get(rootSelectedECD)).booleanValue());
    		tfwTreeFilter.setSynchronizerSelected(
    			((Boolean)get(synchronizerSelectedECD)).booleanValue());
    		tfwTreeFilter.setTerminatorSelected(
    			((Boolean)get(terminatorSelectedECD)).booleanValue());
    		tfwTreeFilter.setTriggeredCommitSelected(
    			((Boolean)get(triggeredCommitSelectedECD)).booleanValue());
    		tfwTreeFilter.setTriggeredConverterSelected(
    			((Boolean)get(triggeredConverterSelectedECD)).booleanValue());
    		tfwTreeFilter.setValidatorSelected(
    			((Boolean)get(validatorSelectedECD)).booleanValue());
    		
    		actionList.runNow();
    	}
	}

    public final Branch getBranch()
    {
        return(branch);
    }
}
