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

            TreeSet currentNodes = new TreeSet();
            for (int j = 0; j < nodes.length; j++) {
                currentNodes.add(new Long(nodes[i]));
            }

            for (int j = 0; j < nodeTos.length; j++) {
                currentNodes.remove(new Long(nodeTos[j]));
            }

            if (currentNodes.size() == 0) {
                currentNodes.add(new Long(nodes[0]));
            }

            TreeMap nodeYs = new TreeMap();
            TreeMap nodeXs = new TreeMap();
            TreeSet nextNodes = new TreeSet();
            HashSet previousNodes = new HashSet();
            long currentLevel = 1;

            Iterator iterator = currentNodes.iterator();
            for (int j = 0; iterator.hasNext(); j++) {
                Object key = iterator.next();

                nodeXs.put(key, new Double((double) j / (double) currentNodes.size()));
                nodeYs.put(key, new Long(0));
            }

            do {
                for (int j = 0; j < nodeFroms.length; j++) {
                    Long fromNode = new Long(nodeFroms[j]);
                    Long toNode = new Long(nodeTos[j]);

                    if (currentNodes.contains(fromNode) && !previousNodes.contains(toNode)) {
                        nextNodes.add(toNode);
                        nodeYs.put(toNode, new Long(currentLevel));
                    }
                }

                iterator = currentNodes.iterator();
                for (int j = 1; iterator.hasNext(); j++) {
                    nodeXs.put(iterator.next(), new Double((double) j / (double) (currentNodes.size() + 1)));
                }

                previousNodes.addAll(currentNodes);
                currentNodes = nextNodes;
                nextNodes = new TreeSet();
                currentLevel++;
            } while (currentNodes.size() != 0);

            iterator = nodeYs.keySet().iterator();
            for (; iterator.hasNext(); ) {
                Object key = iterator.next();
                double value = ((Long) nodeYs.get(key)).longValue() + 1;

                nodeYs.put(key, new Double(value / (double) (currentLevel)));
            }

            double[] nodeXsArray = new double[nodes.length];
            double[] nodeYsArray = new double[nodes.length];

            for (int j = 0; j < nodes.length; j++) {
                Long key = new Long(nodes[j]);

                nodeXsArray[j] = ((Double) nodeXs.get(key)).doubleValue();
                nodeYsArray[j] = ((Double) nodeYs.get(key)).doubleValue();
            }

            nodeClusterXs[i] = DoubleIlaFromArray.create(nodeXsArray);
            nodeClusterYs[i] = DoubleIlaFromArray.create(nodeYsArray);
        }

        set(nodeClusterXsECD, ObjectIlaFromArray.create(nodeClusterXs));
        set(nodeClusterYsECD, ObjectIlaFromArray.create(nodeClusterYs));
    }
}
