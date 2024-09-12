package tfw.visualizer;

import tfw.tsm.Root;
import tfw.tsm.RootProxy;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.visualizer.graph.Graph;
import tfw.visualizer.graph.GraphECD;
import tfw.visualizer.graph.GraphFromRootProxy;

public class NodeEdgeConverter extends TriggeredConverter {
    private final Root root;
    private final GraphECD graphECD;

    public NodeEdgeConverter(Root root, StatelessTriggerECD triggerECD, GraphECD graphECD) {
        super("NodeEdgeConverter", triggerECD, null, new ObjectECD[] {graphECD});

        this.root = root;
        this.graphECD = graphECD;
    }

    protected void convert() {
        Graph graphFromRootProxy = GraphFromRootProxy.create(new RootProxy(root));

        set(graphECD, graphFromRootProxy);
    }
}
