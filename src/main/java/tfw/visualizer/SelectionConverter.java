package tfw.visualizer;

import java.io.IOException;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFill;
import tfw.immutable.ilm.intilm.IntIlm;
import tfw.immutable.ilm.intilm.IntIlmUtil;
import tfw.tsm.Converter;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.BooleanIlaECD;
import tfw.tsm.ecd.ilm.IntIlmECD;

public class SelectionConverter extends Converter {
    private final BooleanECD selectedECD;
    private final BooleanECD buttonOneECD;
    private final BooleanECD buttonTwoECD;
    private final BooleanECD buttonThreeECD;
    private final IntIlmECD pixelNodeTLBRECD;
    private final IntegerECD xMouseECD;
    private final IntegerECD yMouseECD;
    private final BooleanECD controlKeyPressedECD;
    private final BooleanIlaECD selectedNodesECD;

    public SelectionConverter(
            BooleanECD selectedECD,
            BooleanECD buttonOneECD,
            BooleanECD buttonTwoECD,
            BooleanECD buttonThreeECD,
            IntIlmECD pixelNodeTLBRECD,
            IntegerECD xMouseECD,
            IntegerECD yMouseECD,
            BooleanECD controlKeyPressedECD,
            BooleanIlaECD selectedNodesECD) {
        super(
                "SelectionConverter",
                new ObjectECD[] {selectedECD, buttonOneECD, buttonTwoECD, buttonThreeECD},
                new ObjectECD[] {pixelNodeTLBRECD, xMouseECD, yMouseECD, controlKeyPressedECD, selectedNodesECD},
                new ObjectECD[] {selectedNodesECD});

        this.selectedECD = selectedECD;
        this.buttonOneECD = buttonOneECD;
        this.buttonTwoECD = buttonTwoECD;
        this.buttonThreeECD = buttonThreeECD;
        this.pixelNodeTLBRECD = pixelNodeTLBRECD;
        this.xMouseECD = xMouseECD;
        this.yMouseECD = yMouseECD;
        this.controlKeyPressedECD = controlKeyPressedECD;
        this.selectedNodesECD = selectedNodesECD;
    }

    protected void convert() {
        if (((Boolean) get(selectedECD)).booleanValue()
                && ((Boolean) get(buttonOneECD)).booleanValue()
                && !((Boolean) getPreviousTransactionState(buttonOneECD)).booleanValue()
                && !((Boolean) get(buttonTwoECD)).booleanValue()
                && !((Boolean) get(buttonThreeECD)).booleanValue()) {
            int x = ((Integer) get(xMouseECD)).intValue();
            int y = ((Integer) get(yMouseECD)).intValue();

            int[] tlbr = null;
            int width = -1;

            try {
                IntIlm tlbrIlm = (IntIlm) get(pixelNodeTLBRECD);

                tlbr = IntIlmUtil.toArray(tlbrIlm);
                width = (int) tlbrIlm.width();
            } catch (IOException e) {
                return;
            }

            //			int[] tops = tlbr[0];
            //			int[] lefts = tlbr[1];
            //			int[] bottoms = tlbr[2];
            //			int[] rights = tlbr[3];
            BooleanIla ila = BooleanIlaFill.create(false, width);

            if (((Boolean) get(controlKeyPressedECD)).booleanValue()) {
                ila = (BooleanIla) get(selectedNodesECD);
            } else {
                ila = BooleanIlaFill.create(false, width);
            }

            for (int i = width - 1; i >= 0; i--) {
                //				if (tops[i] <= y && y <= bottoms[i] &&
                //					lefts[i] <= x && x <= rights[i])
                //				{
                //					try
                //					{
                //						ila = BooleanIlaMutate.create(ila, i, !ila.toArray(i, 1)[0]);
                //					}
                //					catch (DataInvalidException die)
                //					{
                //						return;
                //					}
                //					break;
                //				}
            }

            set(selectedNodesECD, ila);
        }
    }
}
