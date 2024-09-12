package tfw.visualizer.graph;

import java.io.IOException;

public class GraphFromArrays {
    private GraphFromArrays() {}

    public static Graph create(Object[] nodes, Object[] froms, Object[] tos) {
        return new GraphImpl(nodes, froms, tos);
    }

    private static class GraphImpl implements Graph {
        private final Object[] nodes;
        private final Object[] froms;
        private final Object[] tos;

        private GraphImpl(Object[] nodes, Object[] froms, Object[] tos) {
            this.nodes = nodes.clone();
            this.froms = froms.clone();
            this.tos = tos.clone();
        }

        public long nodesLength() {
            return nodes.length;
        }

        public long edgesLength() {
            return froms.length;
        }

        public void get(
                Object[] nodes,
                int nodesOffset,
                long nodesStart,
                int nodesLength,
                Object[] edgeFroms,
                Object[] edgeTos,
                int edgesOffset,
                long edgesStart,
                int edgesLength)
                throws IOException {
            System.arraycopy(this.nodes, (int) nodesStart, nodes, nodesOffset, nodesLength);
            System.arraycopy(this.froms, (int) edgesStart, edgeFroms, edgesOffset, edgesLength);
            System.arraycopy(this.tos, (int) edgesStart, edgeTos, edgesOffset, edgesLength);
        }
    }
}
