package tfw.visualizer;

import java.awt.Font;
import tfw.awt.ecd.FontECD;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class ZoomConverter extends TriggeredConverter {
    private final FontECD fontECD;
    private final IntegerECD graphWidthECD;
    private final IntegerECD graphHeightECD;
    private final float scale;

    public ZoomConverter(
            String name,
            StatelessTriggerECD triggerECD,
            FontECD fontECD,
            IntegerECD graphWidthECD,
            IntegerECD graphHeightECD,
            float scale) {
        super(
                "ZoomConverter[" + name + "]",
                triggerECD,
                new ObjectECD[] {fontECD, graphWidthECD, graphHeightECD},
                new ObjectECD[] {fontECD, graphWidthECD, graphHeightECD});

        this.fontECD = fontECD;
        this.graphWidthECD = graphWidthECD;
        this.graphHeightECD = graphHeightECD;
        this.scale = scale;
    }

    protected void convert() {
        Font font = (Font) get(fontECD);
        float newSize = (float) Math.floor(font.getSize2D() * scale);
        float graphWidth = ((Integer) get(graphWidthECD)).intValue();
        float graphHeight = ((Integer) get(graphHeightECD)).intValue();

        if (newSize > 0.0f) {
            set(fontECD, font.deriveFont(newSize));
        }

        set(graphWidthECD, (int) (graphWidth * scale));
        set(graphHeightECD, (int) (graphHeight * scale));
    }
}
