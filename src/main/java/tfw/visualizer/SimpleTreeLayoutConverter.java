package tfw.visualizer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaUtil;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.Converter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class SimpleTreeLayoutConverter extends Converter {
    private final ObjectIlaECD nodeClustersECD;
    private final ObjectIlaECD nodeClusterFromsECD;
    private final ObjectIlaECD nodeClusterTosECD;
    private final ObjectIlaECD nodeClusterXsECD;
    private final ObjectIlaECD nodeClusterYsECD;

    public SimpleTreeLayoutConverter(
            ObjectIlaECD nodeClustersECD,
            ObjectIlaECD nodeClusterFromsECD,
            ObjectIlaECD nodeClusterTosECD,
            ObjectIlaECD nodeClusterXsECD,
            ObjectIlaECD nodeClusterYsECD) {
        super(
                "SimpleTreeLayoutConverter",
                new ObjectECD[] {nodeClustersECD, nodeClusterFromsECD, nodeClusterTosECD},
                null,
                new ObjectECD[] {nodeClusterXsECD, nodeClusterYsECD});

        this.nodeClustersECD = nodeClustersECD;
        this.nodeClusterFromsECD = nodeClusterFromsECD;
        this.nodeClusterTosECD = nodeClusterTosECD;
        this.nodeClusterXsECD = nodeClusterXsECD;
        this.nodeClusterYsECD = nodeClusterYsECD;
    }

    protected void convert() {
        Object[] nodeClusters = null;
        Object[] nodeClusterFroms = null;
        Object[] nodeClusterTos = null;

        try {
            final ObjectIla nodeClustersIla = (ObjectIla) get(nodeClustersECD);
            final ObjectIla nodeClusterFromsIla = (ObjectIla) get(nodeClusterFromsECD);
            final ObjectIla nodeClusterTosIla = (ObjectIla) get(nodeClusterTosECD);

            nodeClusters = new Object[(int) nodeClustersIla.length()];
            nodeClusterFroms = new Object[(int) nodeClusterFromsIla.length()];
            nodeClusterTos = new Object[(int) nodeClusterTosIla.length()];

            nodeClustersIla.toArray(nodeClusters, 0, 0, nodeClusters.length);
            nodeClusterFromsIla.toArray(nodeClusterFroms, 0, 0, nodeClusterFroms.length);
            nodeClusterTosIla.toArray(nodeClusterTos, 0, 0, nodeClusterTos.length);
        } catch (DataInvalidException e) {
            return;
        }

        Object[] nodeClusterXs = new Object[nodeClusters.length];
        Object[] nodeClusterYs = new Object[nodeClusters.length];

        for (int i = 0; i < nodeClusters.length; i++) {
            long[] nodes = null;
            long[] nodeTos = null;
            long[] nodeFroms = null;

            try {
                nodes = LongIlaUtil.toArray((LongIla) nodeClusters[i]);
                nodeTos = LongIlaUtil.toArray((LongIla) nodeClusterTos[i]);
                nodeFroms = LongIlaUtil.toArray((LongIla) nodeClusterFroms[i]);
            } catch (DataInvalidException e) {
                return;
            }

            TreeSet<Long> currentNodes = new TreeSet<>();
            for (int j = 0; j < nodes.length; j++) {
                currentNodes.add(nodes[i]);
            }

            for (int j = 0; j < nodeTos.length; j++) {
                currentNodes.remove(nodeTos[j]);
            }

            if (currentNodes.isEmpty()) {
                currentNodes.add(nodes[0]);
            }

            TreeMap<Object, Long> nodeYs = new TreeMap<>();
            TreeMap<Object, Double> nodeXs = new TreeMap<>();
            TreeSet<Long> nextNodes = new TreeSet<>();
            HashSet<Long> previousNodes = new HashSet<>();
            long currentLevel = 1;

            Iterator<Long> iterator = currentNodes.iterator();
            for (int j = 0; iterator.hasNext(); j++) {
                Object key = iterator.next();

                nodeXs.put(key, (double) j / (double) currentNodes.size());
                nodeYs.put(key, 0L);
            }

            do {
                for (int j = 0; j < nodeFroms.length; j++) {
                    Long fromNode = nodeFroms[j];
                    Long toNode = nodeTos[j];

                    if (currentNodes.contains(fromNode) && !previousNodes.contains(toNode)) {
                        nextNodes.add(toNode);
                        nodeYs.put(toNode, currentLevel);
                    }
                }

                iterator = currentNodes.iterator();
                for (int j = 1; iterator.hasNext(); j++) {
                    nodeXs.put(iterator.next(), (double) j / (double) (currentNodes.size() + 1));
                }

                previousNodes.addAll(currentNodes);
                currentNodes = nextNodes;
                nextNodes = new TreeSet<>();
                currentLevel++;
            } while (!currentNodes.isEmpty());

            for (Iterator<Object> keyIterator = nodeYs.keySet().iterator(); keyIterator.hasNext(); ) {
                Object key = keyIterator.next();
                double value = nodeYs.get(key) + 1.0;

                nodeYs.put(key, (long) (value / currentLevel));
            }

            double[] nodeXsArray = new double[nodes.length];
            double[] nodeYsArray = new double[nodes.length];

            for (int j = 0; j < nodes.length; j++) {
                Long key = nodes[j];

                nodeXsArray[j] = nodeXs.get(key);
                nodeYsArray[j] = nodeYs.get(key);
            }

            nodeClusterXs[i] = DoubleIlaFromArray.create(nodeXsArray);
            nodeClusterYs[i] = DoubleIlaFromArray.create(nodeYsArray);
        }

        set(nodeClusterXsECD, ObjectIlaFromArray.create(nodeClusterXs));
        set(nodeClusterYsECD, ObjectIlaFromArray.create(nodeClusterYs));
    }
}
