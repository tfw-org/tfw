package tfw.visualizer;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaCheck;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;

class IndexedEdgesFromNodesAndEdgesTest {
    @Test
    void testIndexedEdgesFromNodesAndEdges() throws Exception {
        Object[] nodesArray = new Object[] {new Object(), new Object(), new Object(), new Object(), new Object()};
        Object[] edgesArray = new Object[] {
            nodesArray[0],
            nodesArray[1],
            nodesArray[2],
            nodesArray[3],
            nodesArray[4],
            nodesArray[3],
            nodesArray[2],
            nodesArray[1],
            nodesArray[0]
        };

        ObjectIla<Object> nodes = ObjectIlaFromArray.create(nodesArray);
        ObjectIla<Object> edges = ObjectIlaFromArray.create(edgesArray);

        long[] indexes = new long[] {0, 1, 2, 3, 4, 3, 2, 1, 0};

        LongIla targetIla = LongIlaFromArray.create(indexes);
        LongIla actualIla = IndexedEdgesFromNodesAndEdges.create(nodes, edges);

        final long epsilon = (long) 0.0;
        LongIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
