package tfw.visualizer;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.objectila.ObjectIla;

public class IndexedEdgesFromNodesAndEdges {
    public static LongIla create(ObjectIla nodes, ObjectIla edges) {
        return new MyLongIla(nodes, edges);
    }

    private static class MyLongIla implements LongIla {
        private final ObjectIla nodes;
        private final ObjectIla edges;

        public MyLongIla(ObjectIla nodes, ObjectIla edges) {
            this.nodes = nodes;
            this.edges = edges;
        }

        public long length() {
            return edges.length();
        }

        @Override
        public void toArray(long[] array, int offset, long start, int length) throws DataInvalidException {
            final Object[] nodeArray = new Object[(int) nodes.length()];
            final Object[] edgeArray = new Object[length];

            nodes.toArray(nodeArray, 0, 0, nodeArray.length);
            edges.toArray(edgeArray, 0, start, edgeArray.length);

            createIndexArray(nodeArray, edgeArray, array, offset, 1);
        }

        @Override
        public void toArray(long[] array, int offset, int stride, long start, int length) throws DataInvalidException {
            final Object[] nodeArray = new Object[(int) nodes.length()];
            final Object[] edgeArray = new Object[length];

            nodes.toArray(nodeArray, 0, 0, nodeArray.length);
            edges.toArray(edgeArray, 0, start, edgeArray.length);

            createIndexArray(nodeArray, edgeArray, array, offset, stride);
        }

        private void createIndexArray(
                Object[] nodeArray, Object[] edgeArray, long[] indexArray, int indexArrayOffset, int stride) {
            for (int i = 0; i < nodeArray.length; i++) {
                for (int j = 0; j < edgeArray.length; j++) {
                    if (nodeArray[i].equals(edgeArray[j])) {
                        indexArray[j * stride + indexArrayOffset] = i;
                    }
                }
            }
        }
    }
}
