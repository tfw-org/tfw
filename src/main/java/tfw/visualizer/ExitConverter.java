package tfw.visualizer;

import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.StatelessTriggerECD;

public class ExitConverter extends TriggeredConverter {
    public ExitConverter(StatelessTriggerECD triggerECD) {
        super("ExitConverter", triggerECD, null, null);
    }

    @Override
    protected void convert() {
        System.exit(0);
    }
}
