package tfw.visualizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.longila.LongIlaUtil;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.Converter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.LongIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class ClusterConverter extends Converter {
    private final LongIlaECD nodeFromsECD;
    private final LongIlaECD nodeTosECD;
    private final ObjectIlaECD nodeClustersECD;
    private final ObjectIlaECD nodeClusterFromsECD;
    private final ObjectIlaECD nodeClusterTosECD;

    public ClusterConverter(
            LongIlaECD nodeFromsECD,
            LongIlaECD nodeTosECD,
            ObjectIlaECD nodeClustersECD,
            ObjectIlaECD nodeClusterFromsECD,
            ObjectIlaECD nodeClusterTosECD) {
        super("ClusterConverter", new ObjectECD[] {nodeFromsECD, nodeTosECD}, null, new ObjectECD[] {
            nodeClustersECD, nodeClusterFromsECD, nodeClusterTosECD
        });

        this.nodeFromsECD = nodeFromsECD;
        this.nodeTosECD = nodeTosECD;
        this.nodeClustersECD = nodeClustersECD;
        this.nodeClusterFromsECD = nodeClusterFromsECD;
        this.nodeClusterTosECD = nodeClusterTosECD;
    }

    protected void convert() {
        long[] nodeFroms = null;
        long[] nodeTos = null;

        try {
            nodeFroms = LongIlaUtil.toArray((LongIla) get(nodeFromsECD));
            nodeTos = LongIlaUtil.toArray((LongIla) get(nodeTosECD));
        } catch (DataInvalidException e) {
            return;
        }

        boolean[] edgeVisited = new boolean[nodeFroms.length];
        TreeSet<Long> nodesInCluster = new TreeSet<>();
        ArrayList<Long> edgeFromsInCluster = new ArrayList<>();
        ArrayList<Long> edgeTosInCluster = new ArrayList<>();
        ArrayList<LongIla> clusters = new ArrayList<>();
        ArrayList<LongIla> edgeFroms = new ArrayList<>();
        ArrayList<LongIla> edgeTos = new ArrayList<>();

        do {
            nodesInCluster.clear();
            edgeFromsInCluster.clear();
            edgeTosInCluster.clear();

            for (int i = 0; i < edgeVisited.length; i++) {
                if (!edgeVisited[i]) {
                    Long nodeFrom = nodeFroms[i];
                    Long nodeTo = nodeTos[i];

                    if (nodesInCluster.isEmpty()) {
                        nodesInCluster.add(nodeFrom);
                        nodesInCluster.add(nodeTo);
                        edgeFromsInCluster.add(nodeFrom);
                        edgeTosInCluster.add(nodeTo);
                        edgeVisited[i] = true;
                    } else if (nodesInCluster.contains(nodeFrom)) {
                        nodesInCluster.add(nodeTo);
                        edgeFromsInCluster.add(nodeFrom);
                        edgeTosInCluster.add(nodeTo);
                        edgeVisited[i] = true;
                    } else if (nodesInCluster.contains(nodeTo)) {
                        nodesInCluster.add(nodeFrom);
                        edgeFromsInCluster.add(nodeFrom);
                        edgeTosInCluster.add(nodeTo);
                        edgeVisited[i] = true;
                    }
                }
            }

            if (!nodesInCluster.isEmpty()) {
                long[] edgeFromsArray = new long[edgeFromsInCluster.size()];
                long[] edgeTosArray = new long[edgeTosInCluster.size()];

                for (int i = 0; i < edgeFromsInCluster.size(); i++) {
                    edgeFromsArray[i] = edgeFromsInCluster.get(i);
                    edgeTosArray[i] = edgeTosInCluster.get(i);
                }

                edgeFroms.add(LongIlaFromArray.create(edgeFromsArray));
                edgeTos.add(LongIlaFromArray.create(edgeTosArray));

                long[] nodesArray = new long[nodesInCluster.size()];

                Iterator<Long> iterator = nodesInCluster.iterator();
                for (int i = 0; i < nodesInCluster.size(); i++) {
                    nodesArray[i] = iterator.next();
                }
                clusters.add(LongIlaFromArray.create(nodesArray));
            }
        } while (!nodesInCluster.isEmpty());

        set(nodeClustersECD, ObjectIlaFromArray.create(clusters.toArray()));
        set(nodeClusterFromsECD, ObjectIlaFromArray.create(edgeFroms.toArray()));
        set(nodeClusterTosECD, ObjectIlaFromArray.create(edgeTos.toArray()));
    }
}
