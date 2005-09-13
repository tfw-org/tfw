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

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import tfw.immutable.ImmutableProxy;

public class ImmutableProxyVisualizer
{
	public ImmutableProxyVisualizer(ImmutableProxy proxy)
	{
		JFrame frame = new JFrame("ImmutableProxy Visualizer: "+
			proxy.getParameters().get("name"));
		
		JTree tree = new JTree(createTreeNode(proxy));
		
		frame.getContentPane().add(tree, BorderLayout.CENTER);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
	
	private static DefaultMutableTreeNode createTreeNode(ImmutableProxy proxy)
	{
		TreeMap map = new TreeMap(proxy.getParameters());
		Object name = map.get("name");
		DefaultMutableTreeNode currentNode = new DefaultMutableTreeNode(
			name == null ? proxy.getClass().toString() : name.toString());
		
		for (Iterator i=map.keySet().iterator() ; i.hasNext() ; )
		{
			Object key = i.next();
			Object value = map.get(key);
			
			if (!(value instanceof ImmutableProxy))
			{
				currentNode.add(new DefaultMutableTreeNode(
						key.toString() + " = " + value.toString()));
			}
		}
		
		for (Iterator i=map.keySet().iterator() ; i.hasNext() ; )
		{
			Object key = i.next();
			Object value = map.get(key);
			
			if (value instanceof ImmutableProxy)
			{
				currentNode.add(createTreeNode((ImmutableProxy)value));
			}
		}
		
		return(currentNode);
	}
}