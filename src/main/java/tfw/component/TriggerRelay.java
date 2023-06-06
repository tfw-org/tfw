package tfw.component;

import tfw.tsm.Converter;
import tfw.tsm.ecd.StatelessTriggerECD;

/**
 * A component which which given a trigger event on one event channel causes a
 * trigger event on another event channel.
 */
public class TriggerRelay extends Converter {
    private final StatelessTriggerECD outputTriggerECD;

    /**
     * Creates a trigger relay component.
     *
     * @param name
     *            The name for this component.
     * @param inputTriggerECD
     *            The trigger which when it fires causes the
     *            <tt>outputTriggerECD</tt> to be fired.
     * @param outputTriggerECD
     *            The trigger to be fired when the <tt>inputTriggerECD</tt>
     *            fires.
     */
    public TriggerRelay(String name, StatelessTriggerECD inputTriggerECD, StatelessTriggerECD outputTriggerECD) {
        super(name, new StatelessTriggerECD[] {inputTriggerECD}, new StatelessTriggerECD[] {outputTriggerECD});
        this.outputTriggerECD = outputTriggerECD;
    }

    protected void convert() {
        trigger(outputTriggerECD);
    }
}
