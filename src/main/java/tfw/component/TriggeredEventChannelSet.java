package tfw.component;

import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class TriggeredEventChannelSet extends TriggeredConverter {
    private final ObjectECD[] outputECDs;
    private final Object[] constants;

    public TriggeredEventChannelSet(String name, StatelessTriggerECD triggerECD, ObjectECD outputECD, Object constant) {
        this(name, triggerECD, new ObjectECD[] {outputECD}, new Object[] {constant});
    }

    public TriggeredEventChannelSet(
            String name, StatelessTriggerECD triggerECD, ObjectECD[] outputECDs, Object[] constants) {
        super("TriggeredEventChannelSet[" + name + "]", triggerECD, null, outputECDs);

        this.outputECDs = new ObjectECD[outputECDs.length];
        System.arraycopy(outputECDs, 0, this.outputECDs, 0, outputECDs.length);
        this.constants = new Object[constants.length];
        System.arraycopy(constants, 0, this.constants, 0, constants.length);
    }

    @Override
    protected void convert() {
        for (int i = 0; i < outputECDs.length; i++) {
            set(outputECDs[i], constants[i]);
        }
    }
}
