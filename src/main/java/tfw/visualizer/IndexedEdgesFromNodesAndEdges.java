package tfw.visualizer;

import java.io.IOException;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.objectila.ObjectIla;

public class IndexedEdgesFromNodesAndEdges {
    public static LongIla create(ObjectIla<Object> nodes, ObjectIla<Object> edges) {
        return new LongIlaImpl(nodes, edges);
    }

    private static class LongIlaImpl implements LongIla {
        private final ObjectIla<Object> nodes;
        private final ObjectIla<Object> edges;

        private LongIlaImpl(ObjectIla<Object> nodes, ObjectIla<Object> edges) {
            this.nodes = nodes;
            this.edges = edges;
        }

        @Override
        public long length() throws IOException {
            return edges.length();
        }

        @Override
        public void get(long[] array, int offset, long start, int length) throws IOException {
            final Object[] nodeArray = new Object[(int) nodes.length()];
            final Object[] edgeArray = new Object[length];

            nodes.get(nodeArray, 0, 0, nodeArray.length);
            edges.get(edgeArray, 0, start, edgeArray.length);

            createIndexArray(nodeArray, edgeArray, array, offset);
        }

        private void createIndexArray(Object[] nodeArray, Object[] edgeArray, long[] indexArray, int indexArrayOffset) {
            for (int i = 0; i < nodeArray.length; i++) {
                for (int j = 0; j < edgeArray.length; j++) {
                    if (nodeArray[i].equals(edgeArray[j])) {
                        indexArray[j + indexArrayOffset] = i;
                    }
                }
            }
        }
    }
}
