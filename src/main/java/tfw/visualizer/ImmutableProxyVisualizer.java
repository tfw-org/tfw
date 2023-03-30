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