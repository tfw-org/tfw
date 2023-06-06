package tfw.visualizer;

import java.awt.Component;
import java.awt.Font;
import tfw.awt.ecd.FontECD;
import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.immutable.ilm.intilm.IntIlm;
import tfw.tsm.Converter;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ilm.DoubleIlmECD;
import tfw.tsm.ecd.ilm.IntIlmECD;
import tfw.visualizer.graph.Graph;
import tfw.visualizer.graph.GraphECD;

public class NormalPixelConverter extends Converter {
    private final Component component;
    private final GraphECD graphECD;
    private final DoubleIlmECD normalizedXYsECD;
    private final IntegerECD graphXOffsetECD;
    private final IntegerECD graphYOffsetECD;
    private final IntegerECD graphWidthECD;
    private final IntegerECD graphHeightECD;
    private final FontECD fontECD;
    private final IntIlmECD pixelNodeTLBRECD;

    public NormalPixelConverter(
            Component component,
            GraphECD graphECD,
            DoubleIlmECD normalizedXYsECD,
            IntegerECD graphXOffsetECD,
            IntegerECD graphYOffsetECD,
            IntegerECD graphWidthECD,
            IntegerECD graphHeightECD,
            FontECD fontECD,
            IntIlmECD pixelNodeTLBRECD) {
        super(
                "NormalPixelConverter",
                new ObjectECD[] {
                    graphECD, normalizedXYsECD, graphXOffsetECD, graphYOffsetECD, graphWidthECD, graphHeightECD, fontECD
                },
                null,
                new ObjectECD[] {pixelNodeTLBRECD});

        this.component = component;
        this.graphECD = graphECD;
        this.normalizedXYsECD = normalizedXYsECD;
        this.graphXOffsetECD = graphXOffsetECD;
        this.graphYOffsetECD = graphYOffsetECD;
        this.graphWidthECD = graphWidthECD;
        this.graphHeightECD = graphHeightECD;
        this.fontECD = fontECD;
        this.pixelNodeTLBRECD = pixelNodeTLBRECD;
    }

    protected void convert() {
        Graph graph = (Graph) get(graphECD);
        DoubleIlm normalizedXYs = (DoubleIlm) get(normalizedXYsECD);
        int graphXOffset = ((Integer) get(graphXOffsetECD)).intValue();
        int graphYOffset = ((Integer) get(graphYOffsetECD)).intValue();
        int graphWidth = ((Integer) get(graphWidthECD)).intValue();
        int graphHeight = ((Integer) get(graphHeightECD)).intValue();
        Font font = (Font) get(fontECD);

        IntIlm pixelNodeBoundsFromNormalizedXYs = PixelNodeBoundsFromNormalizedXYs.create(
                graph,
                normalizedXYs,
                graphXOffset,
                graphYOffset,
                graphWidth,
                graphHeight,
                component.getFontMetrics(font));

        set(pixelNodeTLBRECD, pixelNodeBoundsFromNormalizedXYs);
    }
}
