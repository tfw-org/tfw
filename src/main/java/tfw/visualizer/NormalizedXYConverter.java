package tfw.visualizer;

import tfw.tsm.Converter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ilm.DoubleIlmECD;
import tfw.visualizer.graph.Graph;
import tfw.visualizer.graph.GraphECD;

public class NormalizedXYConverter extends Converter {
    private final GraphECD graphECD;
    private final DoubleIlmECD normalizedXYsECD;

    public NormalizedXYConverter(GraphECD graphECD, DoubleIlmECD normalizedXYsECD) {
        super("NormalizedXYConverter", new ObjectECD[] {graphECD}, null, new ObjectECD[] {normalizedXYsECD});

        this.graphECD = graphECD;
        this.normalizedXYsECD = normalizedXYsECD;
    }

    @Override
    protected void convert() {
        Graph graph = (Graph) get(graphECD);

        set(normalizedXYsECD, NormalXYDoubleIlmFromGraph.create(graph));
    }
}
