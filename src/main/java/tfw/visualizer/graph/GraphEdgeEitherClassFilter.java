package tfw.visualizer.graph;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class GraphEdgeEitherClassFilter {
    private GraphEdgeEitherClassFilter() {}

    public static Graph create(Graph graph, Class<?> classToRemove) {
        Argument.assertNotNull(graph, "graph");
        Argument.assertNotNull(classToRemove, "classToRemove");

        return new MyGraph(graph, classToRemove);
    }

    private static class MyGraph implements Graph {
        private final Graph graph;
        private final Class<?> classToRemove;

        public MyGraph(Graph graph, Class<?> classToRemove) {
            this.graph = graph;
            this.classToRemove = classToRemove;
        }

        public long nodesLength() {
            return graph.nodesLength();
        }

        public long edgesLength() {
            return graph.edgesLength();
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

            int edgesEnd = edgesOffset + edgesLength;

            for (int i = edgesOffset; i < edgesEnd; i++) {
                if (edgeFroms[i] != null
                        && edgeTos[i] != null
                        && (edgeFroms[i].getClass() == classToRemove || edgeTos[i].getClass() == classToRemove)) {
                    edgeFroms[i] = null;
                    edgeTos[i] = null;
                }
            }
        }
    }
}
