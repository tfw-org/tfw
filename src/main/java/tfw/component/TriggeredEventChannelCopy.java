package tfw.component;

import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

/**
 * A component which copies the state of the input channel to the output channel
 * based on a trigger.
 */
public class TriggeredEventChannelCopy extends TriggeredConverter {
    private final ObjectECD inputECD;

    private final ObjectECD outputECD;

    /**
     * Creates a component with specified attributes.
     *
     * @param name
     *            The name of this component.
     * @param triggerECD
     *            The description of the triggering event channel that causes
     *            the copy to be performed.
     * @param inputECD
     *            The description of the input channel to be copied.
     * @param outputECD
     *            The description of the output channel into which the input
     *            channels state is to be copied.
     */
    public TriggeredEventChannelCopy(
            String name, StatelessTriggerECD triggerECD, ObjectECD inputECD, ObjectECD outputECD) {
        super("TriggeredEventChannelCopy[" + name + "]", triggerECD, new ObjectECD[] {inputECD}, new ObjectECD[] {
            outputECD
        });
        if (outputECD.getConstraint().isCompatible(inputECD.getConstraint()) == false) {
            throw new IllegalArgumentException(
                    "outputECD.getConstraint().isCompatible(inputECD.getConstraint()) == false not allowed");
        }

        this.inputECD = inputECD;
        this.outputECD = outputECD;
    }

    @Override
    protected void convert() {
        set(outputECD, get(inputECD));
    }
}
