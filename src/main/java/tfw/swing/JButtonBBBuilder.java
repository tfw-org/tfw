package tfw.swing;

import tfw.awt.ecd.ColorECD;
import tfw.awt.ecd.FontECD;
import tfw.check.Argument;
import tfw.swing.ecd.IconECD;
import tfw.swing.event.ActionListenerFactory;
import tfw.swing.event.SetBackgroundFactory;
import tfw.swing.event.SetEnabledFactory;
import tfw.swing.event.SetFontFactory;
import tfw.swing.event.SetForegroundFactory;
import tfw.swing.event.SetIconFactory;
import tfw.swing.event.SetTextFactory;
import tfw.tsm.Branch;
import tfw.tsm.StateQueueFactory;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;

public class JButtonBBBuilder {
    private ColorECD backgroundColorECD = null;
    private ColorECD foregroundColorECD = null;
    private BooleanECD enabledECD = null;
    private StringECD textECD = null;
    private IconECD iconECD = null;
    private FontECD fontECD = null;
    private StatelessTriggerECD actionECD = null;
    private StateQueueFactory actionOutputStateQueueFactory = null;
    private String name = null;

    public JButtonBBBuilder setBackgroundColorInput(final ColorECD backgroundColorECD) {
        this.backgroundColorECD = backgroundColorECD;

        return this;
    }

    public JButtonBBBuilder setForegroundColorInput(final ColorECD foregroundColorECD) {
        this.foregroundColorECD = foregroundColorECD;

        return this;
    }

    public JButtonBBBuilder setEnabledInput(final BooleanECD enabledECD) {
        this.enabledECD = enabledECD;

        return this;
    }

    public JButtonBBBuilder setTextInput(final StringECD textECD) {
        this.textECD = textECD;

        return this;
    }

    public JButtonBBBuilder setFontInput(final FontECD fontECD) {
        this.fontECD = fontECD;

        return this;
    }

    public JButtonBBBuilder setIconInput(final IconECD iconECD) {
        this.iconECD = iconECD;

        return this;
    }

    public JButtonBBBuilder setActionOutput(final StatelessTriggerECD actionECD) {
        this.actionECD = actionECD;

        return this;
    }

    public JButtonBBBuilder setActionOutputStateQueueFactory(StateQueueFactory actionOutputStateQueueFactory) {
        this.actionOutputStateQueueFactory = actionOutputStateQueueFactory;

        return this;
    }

    public JButtonBBBuilder setName(final String name) {
        this.name = name;

        return this;
    }

    public JButtonBB build() {
        Argument.assertNotNull(name, "name");

        final JButtonBB jButtonBB = new JButtonBB(new Branch(name));

        if (actionECD != null) {
            jButtonBB.addActionListenerToBoth(
                    ActionListenerFactory.create(name, actionECD, actionOutputStateQueueFactory));
        }
        if (enabledECD != null) {
            jButtonBB.getBranch().add(SetEnabledFactory.create(name, enabledECD, jButtonBB, null));
        }
        if (backgroundColorECD != null) {
            jButtonBB.getBranch().add(SetBackgroundFactory.create(name, backgroundColorECD, jButtonBB, null));
        }
        if (foregroundColorECD != null) {
            jButtonBB.getBranch().add(SetForegroundFactory.create(name, foregroundColorECD, jButtonBB, null));
        }
        if (fontECD != null) {
            jButtonBB.getBranch().add(SetFontFactory.create(name, fontECD, jButtonBB, null));
        }
        if (textECD != null) {
            jButtonBB.getBranch().add(SetTextFactory.create(name, textECD, jButtonBB, null));
        }
        if (iconECD != null) {
            jButtonBB.getBranch().add(SetIconFactory.create(name, iconECD, jButtonBB, null));
        }

        return jButtonBB;
    }
}
