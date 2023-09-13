package tfw.visualizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import tfw.check.Argument;
import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.visualizer.graph.Graph;

public class NormalXYDoubleIlmFromGraph {
    private NormalXYDoubleIlmFromGraph() {}

    public static DoubleIlm create(Graph graph) {
        Argument.assertNotNull(graph, "graph");

        return new MyDoubleIlm(graph);
    }

    private static class MyDoubleIlm implements DoubleIlm {
        private final Graph graph;

        public MyDoubleIlm(Graph graph) {
            this.graph = graph;
        }

        public long width() {
            return graph.nodesLength();
        }

        public long height() {
            return 2;
        }

        public double[] get() throws IOException {
            // System.out.println("NormalXYDoubleIlmFromGraph:  starting");
            Object[] nodes = new Object[(int) graph.nodesLength()];
            Object[] froms = new Object[(int) graph.edgesLength()];
            Object[] tos = new Object[(int) graph.edgesLength()];
            double[] normalXs = new double[(int) graph.nodesLength()];
            double[] normalYs = new double[(int) graph.nodesLength()];
            int[] nodeClusterNumbers = new int[(int) graph.nodesLength()];

            graph.get(nodes, 0, 0, (int) graph.nodesLength(), froms, tos, 0, 0, (int) graph.edgesLength());

            // for (int i=0 ; i < nodes.length ; i++)
            // System.out.println("nodes["+i+"]="+nodes[i]);
            // for (int i=0 ; i < froms.length ; i++)
            // System.out.println("froms["+i+"]="+froms[i]+" tos["+i+"]="+tos[i]);

            ArrayList<Object> rootNodes = new ArrayList<>(Arrays.asList(nodes));

            // System.out.println("A: rN.s="+rootNodes.size());
            // Produce the set of root nodes.
            for (int i = 0; i < tos.length; i++) {
                // System.out.println("froms["+i+"]="+froms[i]+" tos["+i+"]="+tos[i]);
                if (tos[i] != null) {
                    // System.out.println("  removing "+tos[i]);
                    rootNodes.remove(tos[i]);
                }
            }
            // System.out.println("B: rN.s="+rootNodes.size());

            // If no roots were found, then just pick the first node.
            if (rootNodes.size() == 0) {
                rootNodes.add(nodes[0]);
            }

            int clusterNumber = 0;
            // System.out.println("rootNodes.size() = "+rootNodes.size());
            while (rootNodes.size() > 0) {
                if (rootNodes.get(0) == null) {
                    rootNodes.remove(0);
                    continue;
                }
                // System.out.println("rootNodes.size() = "+rootNodes.size());
                // For each root, calculate its normal X's, Y depth and
                // node cluster number.
                //	    		int depth = calculateXandYs(0, clusterNumber, new Object[] {rootNodes.remove(0)},
                //	    			nodes, froms, tos, normalXs, normalYs, nodeClusterNumbers);
                ArrayList<ArrayList<Object>> levels = new ArrayList<>();
                ArrayList<Object> rootMinusOneLevel = new ArrayList<>();
                ArrayList<Object> rootLevel = new ArrayList<>();

                rootLevel.add(rootNodes.remove(0));
                levels.add(rootMinusOneLevel);
                levels.add(rootLevel);

                xxx(levels, new HashSet<Object>(Arrays.asList(nodes)), froms, tos);

                // System.out.println("Starting extra root node removal");

                for (int i = rootNodes.size() - 1; i >= 0; i--) {
                    if (rootLevel.contains(rootNodes.get(i))) {
                        rootNodes.remove(i);
                    }
                }

                // System.out.println("Starting normalizing");
                levels.remove(0);

                for (int i = 0; i < levels.size(); i++) {
                    ArrayList<Object> level = levels.get(i);
                    // System.out.println("level="+level);

                    for (int j = 0; j < level.size(); j++) {
                        Object node = level.get(j);

                        for (int k = 0; k < nodes.length; k++) {
                            if (nodes[k] != null && nodes[k].equals(node)) {
                                // System.out.println("node["+k+"] i="+i+" j="+j+" level.s="+level.size()+"
                                // levels.s="+levels.size());
                                normalXs[k] = (double) (j + 1) / (double) (level.size() + 1);
                                normalYs[k] = (double) (i + 1) / (double) (levels.size() + 1);
                                nodeClusterNumbers[k] = clusterNumber;
                            }
                        }
                    }
                }

                // System.out.println("Finished normalizing");

                // System.out.println("depth="+depth);
                // Normalize the Y depth.
                //	    		for (int i=0 ; i < normalYs.length ; i++)
                //	    		{
                //	    			if (normalYs[i] >= 1.0)
                //	    			{
                //	    				normalYs[i] = normalYs[i] / (depth+1);
                //	    			}
                // System.out.println("new normalYs["+i+"]="+normalYs[i]);
                //	    		}
                clusterNumber++;
            }

            // for (int i=0 ; i < normalXs.length ; i++)
            // System.out.println(i+" nX="+normalXs[i]+" nY="+normalYs[i]);

            int dimension = (int) Math.ceil(Math.sqrt(clusterNumber));
            // System.out.println("dimension="+dimension+" cN"+clusterNumber);

            // Position each normalized cluster into a
            // "dimension" x "dimension" grid.
            if (dimension > 0) {
                for (int i = 0; i < normalXs.length; i++) {
                    // System.out.println("normalYs["+i+"]="+normalYs[i]+" nCN["+i+"]="+nodeClusterNumbers[i]+"
                    // d="+dimension);
                    normalXs[i] = normalXs[i] / dimension + (nodeClusterNumbers[i] % dimension) / (double) dimension;
                    normalYs[i] = normalYs[i] / dimension + nodeClusterNumbers[i] / dimension / (double) dimension;
                }
            }

            // System.out.println("NormalXYDoubleIlmFromGraph:  ending");
            return new double[0];
        }

        private static void xxx(
                ArrayList<ArrayList<Object>> levels, HashSet<Object> nodes, Object[] froms, Object[] tos) {
            // System.out.println("Entering xxx");
            ArrayList<Object> previousLevelNodes = levels.get(levels.size() - 2);
            ArrayList<Object> currentLevelNodes = levels.get(levels.size() - 1);
            ArrayList<Object> nextLevelNodes = new ArrayList<>();

            // System.out.println("pLN="+previousLevelNodes);
            // System.out.println("cLN="+currentLevelNodes);
            // System.out.println("nLN="+nextLevelNodes);
            // System.out.println("nodes="+nodes);

            for (int i = 0; i < currentLevelNodes.size(); i++) {
                Object currentLevelNode = currentLevelNodes.get(i);
                // System.out.println("looking for "+currentLevelNode);

                for (int j = 0; j < froms.length; j++) {
                    Object from = froms[j];
                    Object to = tos[j];
                    // System.out.println(j+": from="+from+" to="+to);

                    if (from != null && from.equals(currentLevelNode)) {
                        if (nodes.contains(to)) {
                            // System.out.println("found next node "+to);
                            nextLevelNodes.add(to);
                        }
                        froms[j] = null;
                        tos[j] = null;
                    } else if (to != null && to.equals(currentLevelNode)) {
                        if (nodes.contains(from)) {
                            // System.out.println("found previous node "+from);
                            previousLevelNodes.add(from);
                            nodes.remove(from);
                        }
                        froms[j] = null;
                        tos[j] = null;
                    }
                }

                nodes.remove(currentLevelNode);
            }

            // System.out.println("nextLevelNodes.size()="+nextLevelNodes.size());

            if (nextLevelNodes.size() > 0) {
                levels.add(nextLevelNodes);

                xxx(levels, nodes, froms, tos);
            }
            // System.out.println("Leaving xxx");
        }

        public double[] get(long rowStart, long columnStart, int width, int height) throws IOException {
            //	    	double[][] array = toArray();
            //	    	double[][] returnArray = new double[height][width];
            //
            //	    	for (int i=0 ; i < height ; i++)
            //	    	{
            //	    		System.arraycopy(array[i+(int)rowStart], (int)columnStart, returnArray[i], 0, width);
            //	    	}

            return new double[0];
        }

        @Override
        public void get(double[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
                throws IOException {
            // TODO Auto-generated method stub

        }
    }
}
