package tfw.visualizer;

import java.io.IOException;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaUtil;
import tfw.tsm.Converter;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.BooleanIlaECD;
import tfw.tsm.ecd.ilm.IntIlmECD;

public class MoveSelectionConverter extends Converter {
    private final IntegerECD xMouseECD;
    private final IntegerECD yMouseECD;
    private final BooleanECD selectedECD;
    private final IntIlmECD pixelNodeTLBRECD;
    private final BooleanIlaECD selectedNodesECD;
    private final BooleanECD buttonOnePressedECD;
    private final BooleanECD buttonTwoPressedECD;
    private final BooleanECD buttonThreePressedECD;

    public MoveSelectionConverter(
            IntegerECD xMouseECD,
            IntegerECD yMouseECD,
            BooleanECD selectedECD,
            BooleanIlaECD selectedNodesECD,
            IntIlmECD pixelNodeTLBRECD,
            BooleanECD buttonOnePressedECD,
            BooleanECD buttonTwoPressedECD,
            BooleanECD buttonThreePressedECD) {
        super(
                "MoveSelectionConverter",
                new ObjectECD[] {xMouseECD, yMouseECD, selectedECD},
                new ObjectECD[] {
                    selectedNodesECD, pixelNodeTLBRECD, buttonOnePressedECD, buttonTwoPressedECD, buttonThreePressedECD
                },
                new ObjectECD[] {pixelNodeTLBRECD});

        this.xMouseECD = xMouseECD;
        this.yMouseECD = yMouseECD;
        this.selectedECD = selectedECD;
        this.selectedNodesECD = selectedNodesECD;
        this.pixelNodeTLBRECD = pixelNodeTLBRECD;
        this.buttonOnePressedECD = buttonOnePressedECD;
        this.buttonTwoPressedECD = buttonTwoPressedECD;
        this.buttonThreePressedECD = buttonThreePressedECD;
    }

    @Override
    protected void convert() {
        if (((Boolean) get(selectedECD)).booleanValue()
                && ((Boolean) get(buttonOnePressedECD)).booleanValue()
                && ((Boolean) getPreviousTransactionState(buttonOnePressedECD)).booleanValue()
                && !((Boolean) get(buttonTwoPressedECD)).booleanValue()
                && !((Boolean) get(buttonThreePressedECD)).booleanValue()) {
            boolean[] selectedNodes = null;
            int[][] tlbr = null;
            int[] tops = null;
            int[] lefts = null;
            int[] bottoms = null;
            int[] rights = null;

            try {
                selectedNodes = BooleanIlaUtil.toArray((BooleanIla) get(selectedNodesECD));
                //				tlbr = ((IntIlm)get(pixelNodeTLBRECD)).toArray();

                tops = tlbr[0];
                lefts = tlbr[1];
                bottoms = tlbr[2];
                rights = tlbr[3];
            } catch (IOException e) {
                return;
            }

            int x = ((Integer) get(xMouseECD)).intValue();
            int prevX = ((Integer) getPreviousTransactionState(xMouseECD)).intValue();
            int deltaX = x - prevX;
            int y = ((Integer) get(yMouseECD)).intValue();
            int prevY = ((Integer) getPreviousTransactionState(yMouseECD)).intValue();
            int deltaY = y - prevY;

            for (int i = 0; i < selectedNodes.length; i++) {
                if (selectedNodes[i]) {
                    tops[i] += deltaY;
                    lefts[i] += deltaX;
                    bottoms[i] += deltaY;
                    rights[i] += deltaX;
                }
            }

            //			set(pixelNodeTLBRECD, IntIlmFromArray.create(tlbr));
        }
    }
}
