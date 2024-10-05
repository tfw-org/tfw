package tfw.swing;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import tfw.awt.component.EnabledCommit;
import tfw.awt.ecd.ColorECD;
import tfw.awt.ecd.FontECD;
import tfw.awt.event.ActionInitiator;
import tfw.check.Argument;
import tfw.swing.ecd.IconECD;
import tfw.swing.event.SetBackgroundFactory;
import tfw.swing.event.SetFontFactory;
import tfw.swing.event.SetForegroundFactory;
import tfw.swing.event.SetIconFactory;
import tfw.swing.event.SetTextFactory;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.TreeComponent;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;

public class JButtonBB extends JButton implements BranchBox {
    public interface Builder {
        Builder setBackgroundColorInput(final ColorECD backgroundColorECD);

        Builder setForegroundColorInput(final ColorECD foregroundColorECD);

        Builder setEnabledInput(final BooleanECD enabledECD);

        Builder setTextInput(final StringECD textECD);

        Builder setFontInput(final FontECD fontECD);

        Builder setIconInput(final IconECD iconECD);

        Builder setActionOutput(final StatelessTriggerECD actionECD);

        Builder setName(final String name);

        JButtonBB create();
    }

    private static final long serialVersionUID = 1L;
    private final Branch branch;

    public JButtonBB(String name, BooleanECD enabledECD, StatelessTriggerECD triggerECD) {
        this(new Branch("JButtonBB[" + name + "]"), enabledECD, triggerECD);
    }

    public JButtonBB(Branch branch, BooleanECD enabledECD, StatelessTriggerECD triggerECD) {
        this.branch = branch;

        if (enabledECD != null) {
            branch.add(new EnabledCommit("JButtonBB", enabledECD, this, null));
        }

        addActionListenerToBoth(new ActionInitiator("JButtonBB", triggerECD));
    }

    public void addActionListenerToBoth(final ActionListener actionListener) {
        Argument.assertNotNull(actionListener, "actionListener");

        addActionListener(actionListener);

        if (actionListener instanceof BranchBox) {
            branch.add((BranchBox) actionListener);
        } else if (actionListener instanceof TreeComponent) {
            branch.add((TreeComponent) actionListener);
        } else {
            throw new IllegalArgumentException("listener != BranchBox || TreeComponent not allowed!");
        }
    }

    public void removeActionListenerFromBoth(final ActionListener actionListener) {
        Argument.assertNotNull(actionListener, "actionListener");

        removeActionListener(actionListener);

        if (actionListener instanceof BranchBox) {
            branch.remove((BranchBox) actionListener);
        } else if (actionListener instanceof TreeComponent) {
            branch.remove((TreeComponent) actionListener);
        } else {
            throw new IllegalArgumentException("listener != BranchBox || TreeComponent not allowed!");
        }
    }

    @Override
    public final Branch getBranch() {
        return branch;
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    private static class BuilderImpl implements Builder {
        private ColorECD backgroundColorECD = null;
        private ColorECD foregroundColorECD = null;
        private BooleanECD enabledECD = null;
        private StringECD textECD = null;
        private IconECD iconECD = null;
        private FontECD fontECD = null;
        private StatelessTriggerECD actionECD = null;
        private String name = null;

        @Override
        public Builder setBackgroundColorInput(final ColorECD backgroundColorECD) {
            this.backgroundColorECD = backgroundColorECD;

            return this;
        }

        @Override
        public Builder setForegroundColorInput(final ColorECD foregroundColorECD) {
            this.foregroundColorECD = foregroundColorECD;

            return this;
        }

        @Override
        public Builder setEnabledInput(final BooleanECD enabledECD) {
            this.enabledECD = enabledECD;

            return this;
        }

        @Override
        public Builder setTextInput(final StringECD textECD) {
            this.textECD = textECD;

            return this;
        }

        @Override
        public Builder setFontInput(final FontECD fontECD) {
            this.fontECD = fontECD;

            return this;
        }

        @Override
        public Builder setIconInput(final IconECD iconECD) {
            this.iconECD = iconECD;

            return this;
        }

        @Override
        public Builder setActionOutput(final StatelessTriggerECD actionECD) {
            this.actionECD = actionECD;

            return this;
        }

        @Override
        public Builder setName(final String name) {
            this.name = name;

            return this;
        }

        @Override
        public JButtonBB create() {
            Argument.assertNotNull(name, "name");

            final JButtonBB jButtonBB = new JButtonBB(name, enabledECD, actionECD);

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
}
