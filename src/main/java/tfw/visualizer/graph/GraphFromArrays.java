package tfw.visualizer.graph;

import tfw.immutable.DataInvalidException;

public class GraphFromArrays {
    private GraphFromArrays() {}

    public static Graph create(Object[] nodes, Object[] froms, Object[] tos) {
        return new MyGraph(nodes, froms, tos);
    }

    private static class MyGraph implements Graph {
        private final Object[] nodes;
        private final Object[] froms;
        private final Object[] tos;

        public MyGraph(Object[] nodes, Object[] froms, Object[] tos) {
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
            System.arraycopy(this.nodes, (int) nodesStart, nodes, nodesOffset, nodesLength);
            System.arraycopy(this.froms, (int) edgesStart, edgeFroms, edgesOffset, edgesLength);
            System.arraycopy(this.tos, (int) edgesStart, edgeTos, edgesOffset, edgesLength);
        }
    }
}
