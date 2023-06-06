package tfw.visualizer;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.objectila.ObjectIla;

public class IndexedEdgesFromNodesAndEdges {
    public static LongIla create(ObjectIla nodes, ObjectIla edges) {
        return (new MyLongIla(nodes, edges));
    }

    private static class MyLongIla implements LongIla {
        private final ObjectIla nodes;
        private final ObjectIla edges;

        public MyLongIla(ObjectIla nodes, ObjectIla edges) {
            this.nodes = nodes;
            this.edges = edges;
        }

        public long length() {
            return (edges.length());
        }

        public long[] toArray() throws DataInvalidException {
            Object[] nodeArray = nodes.toArray();
            Object[] edgeArray = edges.toArray();
            long[] indexArray = new long[edgeArray.length];

            createIndexArray(nodeArray, edgeArray, indexArray, 0, 1);

            return (indexArray);
        }

        public long[] toArray(long start, int length) throws DataInvalidException {
            Object[] nodeArray = nodes.toArray();
            Object[] edgeArray = edges.toArray(start, length);
            long[] indexArray = new long[edgeArray.length];

            createIndexArray(nodeArray, edgeArray, indexArray, 0, 1);

            return (indexArray);
        }

        public void toArray(long[] array, int offset, long start, int length) throws DataInvalidException {
            Object[] nodeArray = nodes.toArray();
            Object[] edgeArray = edges.toArray(start, length);

            createIndexArray(nodeArray, edgeArray, array, offset, 1);
        }

        public void toArray(long[] array, int offset, int stride, long start, int length) throws DataInvalidException {
            Object[] nodeArray = nodes.toArray();
            Object[] edgeArray = edges.toArray(start, length);

            createIndexArray(nodeArray, edgeArray, array, offset, stride);
        }

        private void createIndexArray(
                Object[] nodeArray, Object[] edgeArray, long[] indexArray, int indexArrayOffset, int stride) {
            for (int i = 0; i < nodeArray.length; i++) {
                for (int j = 0; j < edgeArray.length; j++) {
                    if (nodeArray[i].equals(edgeArray[j])) {
                        indexArray[(j * stride) + indexArrayOffset] = i;
                    }
                }
            }
        }
    }
}
