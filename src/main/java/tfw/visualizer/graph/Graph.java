package tfw.visualizer.graph;

import tfw.immutable.DataInvalidException;

public interface Graph {
    long nodesLength();

    long edgesLength();

    void toArray(
            Object[] nodes,
            int nodesOffset,
            long nodesStart,
            int nodesLength,
            Object[] edgeFroms,
            Object[] edgeTos,
            int edgesOffset,
            long edgesStart,
            int edgesLength)
            throws DataInvalidException;
}
