package tfw.demo;

import javax.swing.JButton;
import tfw.check.Argument;
import tfw.tsm.Commit;
import tfw.tsm.ecd.ECDUtility;
import tfw.tsm.ecd.ObjectECD;

/**
 * An event handler which sets the enabled state of a button based on
 * equality between two sets of events.
 * <P>
 * The button will be enabled if
 * <code>eventChannel1[i].equals(eventChannel2[i]</code> for each element
 * of the arrays.
 *
 */
public class ButtonEnableHandler extends Commit {
    private final ObjectECD[] eventChannels1;
    private final ObjectECD[] eventChannels2;
    private final JButton button;

    /**
     * Creates a new handler.
     * @param name the name of the handler.
     * @param eventChannels1 event channel set one.
     * @param eventChannels2 event channel set two.
     * @param button the button whose enable state is managed.
     */
    public ButtonEnableHandler(String name, ObjectECD[] eventChannels1, ObjectECD[] eventChannels2, JButton button) {
        super("ButtonEnableHandler[" + name + "]", ECDUtility.concat(eventChannels1, eventChannels2));
        Argument.assertNotNull(eventChannels1, "eventChannels1");
        Argument.assertNotNull(eventChannels2, "eventChannels2");

        if (eventChannels1.length != eventChannels2.length) {
            throw new IllegalArgumentException("eventChannels1.length != eventChannels2.length not allowed");
        }

        this.eventChannels1 = eventChannels1;
        this.eventChannels2 = eventChannels2;
        this.button = button;
    }

    @Override
    protected void commit() {
        for (int i = 0; i < eventChannels1.length; i++) {
            if (!get(eventChannels1[i]).equals(get(eventChannels2[i]))) {
                button.setEnabled(true);

                return;
            }
        }

        button.setEnabled(false);
    }
}
