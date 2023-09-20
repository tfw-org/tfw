package tfw.visualizer;

import java.awt.FontMetrics;
import java.io.IOException;
import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.immutable.ilm.intilm.IntIlm;
import tfw.tsm.Proxy;
import tfw.visualizer.graph.Graph;

public final class PixelNodeBoundsFromNormalizedXYs {
    private PixelNodeBoundsFromNormalizedXYs() {}

    public static IntIlm create(
            Graph graph,
            DoubleIlm normalizedXYs,
            int graphXOffset,
            int graphYOffset,
            int graphWidth,
            int graphHeight,
            FontMetrics fontMetrics) {
        return new IntIlmImpl(graph, normalizedXYs, graphXOffset, graphYOffset, graphWidth, graphHeight, fontMetrics);
    }

    private static class IntIlmImpl implements IntIlm {
        private final Graph graph;
        private final DoubleIlm normalizedXYs;
        private final int graphXOffset;
        private final int graphYOffset;
        private final int graphWidth;
        private final int graphHeight;
        private final FontMetrics fontMetrics;

        private IntIlmImpl(
                Graph graph,
                DoubleIlm normalizedXYs,
                int graphXOffset,
                int graphYOffset,
                int graphWidth,
                int graphHeight,
                FontMetrics fontMetrics) {
            this.graph = graph;
            this.normalizedXYs = normalizedXYs;
            this.graphXOffset = graphXOffset;
            this.graphYOffset = graphYOffset;
            this.graphWidth = graphWidth;
            this.graphHeight = graphHeight;
            this.fontMetrics = fontMetrics;
        }

        public long width() throws IOException {
            return normalizedXYs.width();
        }

        public long height() {
            return 4;
        }

        public int[] get() throws IOException {
            Object[] nodes = new Object[(int) graph.nodesLength()];
            Object[] froms = new Object[0];
            Object[] tos = new Object[0];

            graph.get(nodes, 0, 0, (int) graph.nodesLength(), froms, tos, 0, 0, 0);

            double[][] xys = null; // normalizedXYs.get();
            double[] xs = xys[0];
            double[] ys = xys[1];

            int halfTextHeight = fontMetrics.getHeight() / 2;

            int[] lefts = new int[xs.length];
            int[] rights = new int[xs.length];
            int[] bottoms = new int[ys.length];
            int[] tops = new int[ys.length];

            for (int i = 0; i < nodes.length; i++) {
                if (nodes[i] != null) {
                    int x = graphXOffset + (int) (xs[i] * graphWidth);
                    int y = graphYOffset + (int) (ys[i] * graphHeight);
                    int halfTextWidth = fontMetrics.stringWidth(((Proxy) nodes[i]).getName()) / 2;

                    lefts[i] = x - halfTextWidth;
                    rights[i] = x + halfTextWidth;
                    bottoms[i] = y + halfTextHeight;
                    tops[i] = y - halfTextHeight;
                }
            }

            //	    	return(new int[][] {tops, lefts, bottoms, rights});
            return null;
        }

        public int[] get(long rowStart, long columnStart, int width, int height) throws IOException {
            throw new IOException("Method not implemented!");
        }

        @Override
        public void get(int[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
                throws IOException {
            // TODO Auto-generated method stub

        }
    }
}
