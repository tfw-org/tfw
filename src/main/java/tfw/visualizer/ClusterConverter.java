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
        TreeSet nodesInCluster = new TreeSet();
        ArrayList edgeFromsInCluster = new ArrayList();
        ArrayList edgeTosInCluster = new ArrayList();
        int clusterNumber = 1;
        ArrayList clusters = new ArrayList();
        ArrayList edgeFroms = new ArrayList();
        ArrayList edgeTos = new ArrayList();

        do {
            nodesInCluster.clear();
            edgeFromsInCluster.clear();
            edgeTosInCluster.clear();

            for (int i = 0; i < edgeVisited.length; i++) {
                if (!edgeVisited[i]) {
                    Long nodeFrom = new Long(nodeFroms[i]);
                    Long nodeTo = new Long(nodeTos[i]);

                    if (nodesInCluster.size() == 0) {
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

            if (nodesInCluster.size() != 0) {
                long[] edgeFromsArray = new long[edgeFromsInCluster.size()];
                long[] edgeTosArray = new long[edgeTosInCluster.size()];

                for (int i = 0; i < edgeFromsInCluster.size(); i++) {
                    edgeFromsArray[i] = ((Long) edgeFromsInCluster.get(i)).longValue();
                    edgeTosArray[i] = ((Long) edgeTosInCluster.get(i)).longValue();
                }

                edgeFroms.add(LongIlaFromArray.create(edgeFromsArray));
                edgeTos.add(LongIlaFromArray.create(edgeTosArray));

                long[] nodesArray = new long[nodesInCluster.size()];

                Iterator iterator = nodesInCluster.iterator();
                for (int i = 0; i < nodesInCluster.size(); i++) {
                    nodesArray[i] = ((Long) iterator.next()).longValue();
                }
                clusters.add(LongIlaFromArray.create(nodesArray));

                clusterNumber++;
            }
        } while (nodesInCluster.size() != 0);

        set(nodeClustersECD, ObjectIlaFromArray.create(clusters.toArray()));
        set(nodeClusterFromsECD, ObjectIlaFromArray.create(edgeFroms.toArray()));
        set(nodeClusterTosECD, ObjectIlaFromArray.create(edgeTos.toArray()));
    }
}
