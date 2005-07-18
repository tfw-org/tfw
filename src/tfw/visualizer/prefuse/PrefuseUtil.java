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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import tfw.tsm.Branch;
import tfw.tsm.BranchProxy;
import tfw.tsm.Commit;
import tfw.tsm.CommitProxy;
import tfw.tsm.Converter;
import tfw.tsm.Initiator;
import tfw.tsm.InitiatorProxy;
import tfw.tsm.Root;
import tfw.tsm.SinkProxy;
import tfw.tsm.SourceProxy;
import tfw.tsm.Synchronizer;
import tfw.tsm.TerminatorProxy;
import tfw.tsm.TreeComponent;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.TriggeredCommitProxy;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.Validator;
import edu.berkeley.guir.prefuse.graph.DefaultEdge;
import edu.berkeley.guir.prefuse.graph.DefaultTreeNode;

public class PrefuseUtil
{
	private PrefuseUtil() {}
	
    public static void createNodes(
        	TreeComponent treeComponentParent,
        	DefaultTreeNode treeNodeParent, ArrayList nodes,
			Map proxies)
    {
    	if (treeComponentParent instanceof Branch)
    	{
	    	Map children = treeComponentParent.getChildren();
	    	
    		if (treeComponentParent instanceof Branch)
    		{
    			Branch b = (Branch)treeComponentParent;
    			BranchProxy bp = new BranchProxy(b);
    			TerminatorProxy[] tp = bp.getTerminatorProxies();
    			
    			for (int i=0 ; i < tp.length ; i++)
    			{
    				DefaultTreeNode terminatorNode = new DefaultTreeNode();
    				DefaultEdge terminatorEdge = new DefaultEdge(
    					treeNodeParent, terminatorNode, false);
    				
    				terminatorNode.setAttribute("label",
    					tp[i].getEventChannelDescription()
						.getEventChannelName());
    				terminatorNode.setAttribute("tfw_type", "terminator");
    				nodes.add(terminatorNode);
    				proxies.put(tp[i], terminatorNode);
    				treeNodeParent.addChild(terminatorEdge);
    			}
    		}
    		
	    	for (Iterator i=children.values().iterator() ; i.hasNext() ; )
	    	{
	    		TreeComponent treeComponentChild = (TreeComponent)i.next();
	    		DefaultTreeNode treeNodeChild = new DefaultTreeNode();
	    		DefaultEdge edge = new DefaultEdge(
	    			treeNodeParent, treeNodeChild, false);
	    		
	    		if (treeComponentChild instanceof Root)
	    		{
	    			treeNodeChild.setAttribute(
	    				"tfw_type", "root");
	    		}
	    		else if (treeComponentChild instanceof Branch)
	    		{
	    			treeNodeChild.setAttribute(
	    				"tfw_type", "branch");
	    		}
	    		else if (treeComponentChild instanceof Commit)
	    		{
	    			CommitProxy cp = new CommitProxy(
	    				(Commit)treeComponentChild);
	    			
	    			addSinks(cp.getSinkProxies(), proxies, treeNodeChild);
	    			treeNodeChild.setAttribute(
	    				"tfw_type", "commit");
	    		}
	    		else if (treeComponentChild instanceof Converter)
	    		{
	    			treeNodeChild.setAttribute(
	    				"tfw_type", "converter");
	    		}
	    		else if (treeComponentChild instanceof Initiator)
	    		{
	    			InitiatorProxy ip = new InitiatorProxy(
	    				(Initiator)treeComponentChild);
	    			
	    			addSources(ip.getSourceProxies(), proxies, treeNodeChild);
	    			treeNodeChild.setAttribute(
	    				"tfw_type", "initiator");
	    		}
	    		else if (treeComponentChild instanceof Synchronizer)
	    		{
	    			treeNodeChild.setAttribute(
	    				"tfw_type", "synchronizer");
	    		}
	    		else if (treeComponentChild instanceof TriggeredCommit)
	    		{
	    			TriggeredCommitProxy tcp = new TriggeredCommitProxy(
	    				(TriggeredCommit)treeComponentChild);

	    			addSinks(tcp.getSinkProxies(), proxies, treeNodeChild);
	    			treeNodeChild.setAttribute(
	    				"tfw_type", "triggeredCommit");
	    		}
	    		else if (treeComponentChild instanceof TriggeredConverter)
	    		{
	    			treeNodeChild.setAttribute(
	    				"tfw_type", "triggeredConverter");
	    		}
	    		else if (treeComponentChild instanceof Validator)
	    		{
	    			treeNodeChild.setAttribute(
	    				"tfw_type", "validator");
	    		}
	    		
	    		treeNodeChild.setAttribute(
	    			"label", treeComponentChild.getName());
	    		nodes.add(treeNodeChild);
	    		treeNodeParent.addChild(edge);
	    		createNodes(treeComponentChild, treeNodeChild, nodes, proxies);
	    	}
    	}
    }
    
    public static void addSources(SourceProxy[] sourceProxies, Map proxies,
        DefaultTreeNode treeNodeChild)
    {
		for (int i=0 ; i < sourceProxies.length ; i++)
		{
			DefaultTreeNode terminatorNode =
				(DefaultTreeNode)proxies.get(
				sourceProxies[i].getTerminatorProxy());
			
			if (terminatorNode == null)
				throw new IllegalStateException(
					"Could not get Terminator Proxy/Node!");
			
			DefaultEdge terminatorEdge = new DefaultEdge(
				treeNodeChild, terminatorNode);
			treeNodeChild.addChild(terminatorEdge);
		}
    }
    
    public static void addSinks(SinkProxy[] sinkProxies, Map proxies,
    	DefaultTreeNode treeNodeChild)
    {
		for (int i=0 ; i < sinkProxies.length ; i++)
		{
			DefaultTreeNode terminatorNode =
				(DefaultTreeNode)proxies.get(
    			sinkProxies[i].getTerminatorProxy());
    				
			if (terminatorNode == null)
				throw new IllegalStateException(
					"Could not get Terminator Proxy/Node!");
				
			DefaultEdge terminatorEdge = new DefaultEdge(
					terminatorNode, treeNodeChild);
			terminatorNode.addChild(terminatorEdge);
		}
    }
}