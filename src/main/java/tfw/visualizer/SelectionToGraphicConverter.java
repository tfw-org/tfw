package tfw.visualizer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import tfw.awt.ecd.GraphicECD;
import tfw.awt.graphic.Graphic;
import tfw.awt.graphic.SetColorGraphic;
import tfw.awt.graphic.SetStrokeGraphic;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaUtil;
import tfw.immutable.ilm.intilm.IntIlm;
import tfw.immutable.ilm.intilm.IntIlmUtil;
import tfw.tsm.Converter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.BooleanIlaECD;
import tfw.tsm.ecd.ilm.IntIlmECD;

public class SelectionToGraphicConverter extends Converter {
    private final BooleanIlaECD selectedNodesECD;
    private final IntIlmECD pixelNodeTLBRECD;
    private final GraphicECD graphicECD;

    public SelectionToGraphicConverter(
            BooleanIlaECD selectedNodesECD, IntIlmECD pixelNodeTLBRECD, GraphicECD graphicECD) {
        super(
                "SelectionToGraphicConverter",
                new ObjectECD[] {selectedNodesECD, pixelNodeTLBRECD},
                null,
                new ObjectECD[] {graphicECD});

        this.selectedNodesECD = selectedNodesECD;
        this.pixelNodeTLBRECD = pixelNodeTLBRECD;
        this.graphicECD = graphicECD;
    }

    protected void convert() {
        boolean[] selectedNodes = null;
        int[] tlbr = null;
        int width = -1;
        Graphic graphic = SetStrokeGraphic.create(SetColorGraphic.create(null, Color.red), new BasicStroke(3.0f));

        try {
            selectedNodes = BooleanIlaUtil.toArray((BooleanIla) get(selectedNodesECD));

            IntIlm intIlm = (IntIlm) get(pixelNodeTLBRECD);
            tlbr = IntIlmUtil.toArray(intIlm);
            width = (int) intIlm.width();
        } catch (DataInvalidException | IOException e) {
            return;
        }

        //		int[] tops = tlbr[0];
        //		int[] lefts = tlbr[1];
        //		int[] bottoms = tlbr[2];
        //		int[] rights = tlbr[3];

        for (int i = 0; i < width; i++) {
            if (i < selectedNodes.length && selectedNodes[i]) {
                //				graphic = DrawRectGraphic.create(graphic, lefts[i]-1, tops[i]-1,
                //					rights[i]-lefts[i]+2, bottoms[i]-tops[i]+2);
            }
        }

        set(graphicECD, graphic);
    }
}
