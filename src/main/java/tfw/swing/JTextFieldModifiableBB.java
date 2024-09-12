package tfw.swing;

import java.awt.Color;
import tfw.awt.event.ActionInitiator;
import tfw.check.Argument;
import tfw.component.EventChannelCopyConverter;
import tfw.tsm.Branch;
import tfw.tsm.Commit;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;

public class JTextFieldModifiableBB extends JTextFieldBB {
    private static final long serialVersionUID = 1L;

    private final Color defaultDisabledBackground;

    private final Color defaultEnabledBackground;

    private final Color modifiedBackground = new Color(153, 153, 204);

    public JTextFieldModifiableBB(
            String name, StringECD textECD, StringECD textAdjECD, BooleanECD enabledECD, StatelessTriggerECD applyECD) {
        this(new Branch("JTextFieldModifiableBB[" + name + "]"), textECD, textAdjECD, enabledECD, applyECD);
    }

    public JTextFieldModifiableBB(
            Branch branch,
            StringECD textECD,
            StringECD textAdjECD,
            BooleanECD enabledECD,
            StatelessTriggerECD applyECD) {
        super(branch, textAdjECD, enabledECD);

        setEnabled(false);
        defaultDisabledBackground = getBackground();
        setEnabled(true);
        defaultEnabledBackground = getBackground();

        ActionInitiator actionInitiator = new ActionInitiator("JTextFieldModifiableBB", applyECD);
        addActionListenerToBoth(actionInitiator);

        branch.add(new ForegroundBackgroundHandler(branch.getName(), textECD, textAdjECD, enabledECD));
        branch.add(new EventChannelCopyConverter(branch.getName(), textECD, textAdjECD));
    }

    private static ObjectECD[] toArray(StringECD textSink, StringECD textAdjSink, BooleanECD enableSink) {
        Argument.assertNotNull(textSink, "textSink");
        Argument.assertNotNull(textAdjSink, "textAdjSink");

        if (enableSink == null) {
            return new ObjectECD[] {textSink, textAdjSink};
        } else {
            return new ObjectECD[] {textSink, textAdjSink, enableSink};
        }
    }

    /**
     * Sets the foreground and background state of the text field. Sets the
     * value of the text field if the text event channel is set during the state
     * change cycle.
     */
    private final class ForegroundBackgroundHandler extends Commit {
        private final StringECD textName;

        private final StringECD textAdjName;

        private final BooleanECD enableName;

        public ForegroundBackgroundHandler(
                String name, StringECD textSink, StringECD textAdjSink, BooleanECD enableSink) {
            super("ForegroundBackgroundHandler[" + name + "]", toArray(textSink, textAdjSink, enableSink));

            this.textName = textSink;
            this.textAdjName = textAdjSink;
            this.enableName = enableSink;
        }

        protected void commit() {
            String text = (String) get(textName);
            String textAdj = (String) get(textAdjName);

            boolean enabled = true;
            if (this.enableName != null) {
                enabled = ((Boolean) get(enableName)).booleanValue();
            }

            if (!text.equals(textAdj)) {
                setBackground(modifiedBackground);
            } else if (enabled) {
                setBackground(defaultEnabledBackground);
                setForeground(Color.black);
            } else {
                setBackground(defaultDisabledBackground);
                setForeground(Color.black);
            }
        }
    }
}
