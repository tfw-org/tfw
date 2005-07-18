package tfw.visualizer.prefuse;

import java.util.Iterator;

import edu.berkeley.guir.prefuse.EdgeItem;
import edu.berkeley.guir.prefuse.ItemRegistry;
import edu.berkeley.guir.prefuse.NodeItem;
import edu.berkeley.guir.prefuse.action.filter.Filter;
import edu.berkeley.guir.prefuse.graph.Edge;
import edu.berkeley.guir.prefuse.graph.Graph;
import edu.berkeley.guir.prefuse.graph.GraphLib;
import edu.berkeley.guir.prefuse.graph.Node;
import edu.berkeley.guir.prefuse.graph.Tree;

/**
 * Filters a tree in it's entirety. If the backing graph is not a tree,
 * a tree structure will still be imposed on the filtered graph.
 * By default, garbage collection on node and edge items is performed.
 *
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public class TFWTreeFilter extends Filter {

    public static final String[] ITEM_CLASSES = 
        {ItemRegistry.DEFAULT_NODE_CLASS, ItemRegistry.DEFAULT_EDGE_CLASS};
    
    // determines if filtered edges are visible by default
    private boolean m_edgesVisible;
    private boolean m_useFocusAsRoot;
    private Node m_root;
    
    private boolean branchSelected = true;
    private boolean commitSelected = true;
    private boolean converterSelected = true;
    private boolean initiatorSelected = true;
    private boolean rootSelected = true;
    private boolean synchronizerSelected = true;
    private boolean terminatorSelected = true;
    private boolean triggeredCommitSelected = true;
    private boolean triggeredConverterSelected = true;
    private boolean validatorSelected = true;
    
    // ========================================================================
    // == CONSTRUCTORS ========================================================
    
    public TFWTreeFilter() {
        this(false, true, true);
    } //
    
    public TFWTreeFilter(boolean useFocusAsRoot) {
        this(useFocusAsRoot, true, true);
    } //
    
    public TFWTreeFilter(boolean useFocusAsRoot, boolean edgesVisible) {
        this(useFocusAsRoot, edgesVisible, true);
    } //
    
    public TFWTreeFilter(boolean useFocusAsRoot, boolean edgesVisible, boolean garbageCollect) {
        super(ITEM_CLASSES, garbageCollect);
        m_edgesVisible = edgesVisible;
        m_useFocusAsRoot = useFocusAsRoot;
    } //
    
    // ========================================================================
    // == FILTER METHODS ======================================================
    
    public void setTreeRoot(Node r) {
        m_root = r;
    } //
    
    public void run(ItemRegistry registry, double frac) {
        Graph graph = registry.getGraph();
        boolean isTree = (graph instanceof Tree);
        NodeItem root = null;
        
        // filter the nodes
        Iterator nodeIter = graph.getNodes();
        while ( nodeIter.hasNext() )
        {
        	Node node = (Node)nodeIter.next();
        	String tfwType = node.getAttribute("tfw_type");
        	
if (tfwType == null)
	throw new IllegalStateException(
		"tfwType == null for node "+node);

        	boolean visible = false;
        	
        	if ((tfwType.equals("branch") && branchSelected) ||
        		(tfwType.equals("commit") && commitSelected) ||
				(tfwType.equals("converter") && converterSelected) ||
				(tfwType.equals("initiator") && initiatorSelected) ||
				(tfwType.equals("root") && rootSelected) ||
				(tfwType.equals("synchronizer") && synchronizerSelected) ||
				(tfwType.equals("terminator") && terminatorSelected) ||
				(tfwType.equals("triggeredCommit") && triggeredCommitSelected) ||
				(tfwType.equals("triggeredConverter") && triggeredConverterSelected) ||
				(tfwType.equals("validator") && validatorSelected))
        	{
        		visible = true;
        	}
System.out.println("node ["+node+"] = "+visible);
       	
            NodeItem item = registry.getNodeItem(node, visible, false);
//          NodeItem item = registry.getNodeItem((Node)nodeIter.next(), true, true);
            if ( root == null ) root = item;
        }
        
        Iterator fiter = registry.getDefaultFocusSet().iterator();
        NodeItem focus = null;
        if ( fiter.hasNext() ) {
            focus = registry.getNodeItem((Node)fiter.next(), true, true);
        }
        
        // update root node as necessary
        if ( m_root != null ) {
            Node r = (m_root instanceof NodeItem ? m_root : 
                        registry.getNodeItem(m_root));
            root = (NodeItem)r;
        } else if ( focus != null && m_useFocusAsRoot ) {
            root = focus;
        } else if ( isTree ) {
            root = registry.getNodeItem(((Tree)graph).getRoot());
        }
        
        // process each node's edges
        nodeIter = registry.getNodeItems();
        while ( nodeIter.hasNext() ) {
            NodeItem item = (NodeItem)nodeIter.next();
            if ( item.getDirty() > 0 )
                continue;
            Node     node = (Node)item.getEntity();
            Iterator edgeIter = node.getEdges();
            while ( edgeIter.hasNext() ) {
                Edge edge = (Edge)edgeIter.next();
                Node n = edge.getAdjacentNode(node);
                // filter the edge
                EdgeItem eitem = registry.getEdgeItem(edge, true);
                eitem.getFirstNode().addEdge(eitem);
                if ( !eitem.isDirected() )
                    eitem.getSecondNode().addEdge(eitem);
                if ( !m_edgesVisible ) eitem.setVisible(false);
            }
        }
        // we need to tree-ify things now
        Tree ftree = GraphLib.breadthFirstTree(root);
        
        // update the registry's filtered graph
        registry.setFilteredGraph(ftree);
        
        // optional garbage collection
        super.run(registry, frac);
    } //    
    
	public boolean isEdgesVisible() {
		return m_edgesVisible;
	} //
	
	public void setEdgesVisible(boolean visible) {
		m_edgesVisible = visible;
	} //
	
	public boolean isUseFocusAsRoot() {
		return m_useFocusAsRoot;
	} //
	
	public void setUseFocusAsRoot(boolean focusAsRoot) {
		m_useFocusAsRoot = focusAsRoot;
	} //
	
	public void setBranchSelected(boolean branchSelected)
	{
		this.branchSelected = branchSelected;
	}
	
	public void setCommitSelected(boolean commitSelected)
	{
		this.commitSelected = commitSelected;
	}
	
	public void setConverterSelected(boolean converterSelected)
	{
		this.converterSelected = converterSelected;
	}
	
	public void setInitiatorSelected(boolean initiatorSelected)
	{
		this.initiatorSelected = initiatorSelected;
	}
	
	public void setRootSelected(boolean rootSelected)
	{
		this.rootSelected = rootSelected;
	}
	
	public void setSynchronizerSelected(boolean synchronizerSelected)
	{
		this.synchronizerSelected = synchronizerSelected;
	}
	
	public void setTerminatorSelected(boolean terminatorSelected)
	{
		this.terminatorSelected = terminatorSelected;
	}
	
	public void setTriggeredCommitSelected(boolean triggeredCommitSelected)
	{
		this.triggeredCommitSelected = triggeredCommitSelected;
	}
	
	public void setTriggeredConverterSelected(
		boolean triggeredConverterSelected)
	{
		this.triggeredConverterSelected = triggeredConverterSelected;
	}
	
	public void setValidatorSelected(boolean validatorSelected)
	{
		this.validatorSelected = validatorSelected;
	}
    
} // end of class TreeFilter
