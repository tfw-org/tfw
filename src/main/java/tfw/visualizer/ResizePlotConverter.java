package tfw.visualizer;

import tfw.tsm.Converter;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;

public class ResizePlotConverter extends Converter {
    private final BooleanECD selectedECD;
    private final IntegerECD xECD;
    private final IntegerECD yECD;
    private final BooleanECD button1ECD;
    private final BooleanECD button2ECD;
    private final BooleanECD button3ECD;
    private final IntegerECD graphWidthECD;
    private final IntegerECD graphHeightECD;

    public ResizePlotConverter(
            BooleanECD selectedECD,
            IntegerECD xECD,
            IntegerECD yECD,
            BooleanECD button1ECD,
            BooleanECD button2ECD,
            BooleanECD button3ECD,
            IntegerECD graphWidthECD,
            IntegerECD graphHeightECD) {
        super(
                "ResizePlotConverter",
                new ObjectECD[] {selectedECD, xECD, yECD, button1ECD, button2ECD, button3ECD},
                new ObjectECD[] {graphWidthECD, graphHeightECD},
                new ObjectECD[] {graphWidthECD, graphHeightECD});

        this.selectedECD = selectedECD;
        this.xECD = xECD;
        this.yECD = yECD;
        this.button1ECD = button1ECD;
        this.button2ECD = button2ECD;
        this.button3ECD = button3ECD;
        this.graphWidthECD = graphWidthECD;
        this.graphHeightECD = graphHeightECD;
    }

    protected void convert() {
        if (((Boolean) get(selectedECD)).booleanValue()) {
            boolean button1 = ((Boolean) get(button1ECD)).booleanValue();
            boolean button2 = ((Boolean) get(button2ECD)).booleanValue();
            boolean button3 = ((Boolean) get(button3ECD)).booleanValue();

            if (!button1 && !button2 && button3) {
                int x = ((Integer) get(xECD)).intValue();
                int y = ((Integer) get(yECD)).intValue();
                int previousX = ((Integer) getPreviousCycleState(xECD)).intValue();
                int previousY = ((Integer) getPreviousCycleState(yECD)).intValue();
                int graphWidth = ((Integer) get(graphWidthECD)).intValue();
                int graphHeight = ((Integer) get(graphHeightECD)).intValue();

                set(graphWidthECD, graphWidth + (x - previousX));
                set(graphHeightECD, graphHeight + (y - previousY));
            }
        }
    }
}
