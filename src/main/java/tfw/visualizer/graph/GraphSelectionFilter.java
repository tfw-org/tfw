package tfw.visualizer.graph;

import java.util.HashSet;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.booleanila.BooleanIla;

public class GraphSelectionFilter {
    private GraphSelectionFilter() {}

    public static Graph create(Graph graph, BooleanIla selectedNodes) {
        return (new MyGraph(graph, selectedNodes));
    }

    private static class MyGraph implements Graph {
        private final Graph graph;
        private final BooleanIla selectedNodes;

        public MyGraph(Graph graph, BooleanIla selectedNodes) {
            this.graph = graph;
            this.selectedNodes = selectedNodes;
        }

        public long nodesLength() {
            return (graph.nodesLength());
        }

        public long edgesLength() {
            return (graph.edgesLength());
        }

        public void toArray(
                Object[] nodes,
                int nodesOffset,
                long nodesStart,
                int nodesLength,
                Object[] edgeFroms,
                Object[] edgeTos,
                int edgesOffset,
                long edgesStart,
                int edgesLength)
                throws DataInvalidException {
            Object[] allNodes = new Object[(int) graph.nodesLength()];
            Object[] allFroms = new Object[(int) graph.edgesLength()];
            Object[] allTos = new Object[(int) graph.edgesLength()];
            boolean[] selectedNodesArray = selectedNodes.toArray();

            graph.toArray(allNodes, 0, 0, (int) graph.nodesLength(), allFroms, allTos, 0, 0, (int) graph.edgesLength());

            HashSet selectedNodeSet = new HashSet();
            for (int i = 0; i < selectedNodesArray.length; i++) {
                if (selectedNodesArray[i]) {
                    selectedNodeSet.add(allNodes[i]);
                }
            }

            HashSet resultNodeSet = new HashSet(selectedNodeSet);
            for (int i = 0; i < allFroms.length; i++) {
                if (selectedNodeSet.contains(allFroms[i])) {
                    resultNodeSet.add(allTos[i]);
                } else if (selectedNodeSet.contains(allTos[i])) {
                    resultNodeSet.add(allFroms[i]);
                }
            }

            graph.toArray(
                    nodes,
                    nodesOffset,
                    nodesStart,
                    nodesLength,
                    edgeFroms,
                    edgeTos,
                    edgesOffset,
                    edgesStart,
                    edgesLength);

            int nodesEnd = nodesOffset + nodesLength;

            for (int i = nodesOffset; i < nodesEnd; i++) {
                if (!resultNodeSet.contains(nodes[i])) {
                    nodes[i] = null;
                }
            }

            int edgesEnd = edgesOffset + edgesLength;

            for (int i = edgesOffset; i < edgesEnd; i++) {
                if (!selectedNodeSet.contains(edgeFroms[i]) && !selectedNodeSet.contains(edgeTos[i])) {
                    edgeFroms[i] = null;
                    edgeTos[i] = null;
                }
            }
        }
    }
}
