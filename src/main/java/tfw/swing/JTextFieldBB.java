package tfw.swing;

import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import tfw.check.Argument;
import tfw.swing.event.DocumentListenerFactory;
import tfw.swing.event.SetEnabledFactory;
import tfw.swing.event.SetTextFactory;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.StateQueueFactory;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.StringECD;

public class JTextFieldBB extends JTextField implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final transient Branch branch;

    public JTextFieldBB(Branch branch) {
        Argument.assertNotNull(branch, "branch");

        this.branch = branch;
    }

    public void addActionListenerToBoth(ActionListener actionListener) {
        SwingUtil.addObjectToBranch(actionListener, branch);
        addActionListener(actionListener);
    }

    public void removeActionListenerFromBoth(ActionListener actionListener) {
        SwingUtil.removeObjectFromBranch(actionListener, branch);
        removeActionListener(actionListener);
    }

    public void addDocumentListenerToBoth(DocumentListener documentListener) {
        SwingUtil.addObjectToBranch(documentListener, branch);
        getDocument().addDocumentListener(documentListener);
    }

    public void removeDocumentListenerFromBoth(DocumentListener documentListener) {
        SwingUtil.removeObjectFromBranch(documentListener, branch);
        getDocument().removeDocumentListener(documentListener);
    }

    @Override
    public final Branch getBranch() {
        return branch;
    }

    public static JTextFieldBBBuilder builder() {
        return new JTextFieldBBBuilder();
    }

    public static class JTextFieldBBBuilder {
        private String name = null;
        private BooleanECD enabledInputECD = null;
        private StringECD textInputOutputECD = null;
        private StateQueueFactory textOutputStateQueueFactory;

        public JTextFieldBBBuilder setName(final String name) {
            this.name = name;

            return this;
        }

        public JTextFieldBBBuilder setEnabledInputECD(final BooleanECD enabledInputECD) {
            this.enabledInputECD = enabledInputECD;

            return this;
        }

        public JTextFieldBBBuilder setTextInputOutputECD(final StringECD textInputOutputECD) {
            this.textInputOutputECD = textInputOutputECD;

            return this;
        }

        public JTextFieldBBBuilder setTextOutputStateQueueFactory(StateQueueFactory textOutputStateQueueFactory) {
            this.textOutputStateQueueFactory = textOutputStateQueueFactory;

            return this;
        }

        public JTextFieldBB build() {
            Argument.assertNotNull(name, "name");

            final JTextFieldBB jTextFieldBB = new JTextFieldBB(new Branch("JTextFieldBB[" + name + "]"));

            if (enabledInputECD != null) {
                jTextFieldBB.getBranch().add(SetEnabledFactory.create(name, enabledInputECD, jTextFieldBB, null));
            }

            if (textInputOutputECD != null) {
                final DocumentListener documentListener =
                        DocumentListenerFactory.create(name, textInputOutputECD, textOutputStateQueueFactory);
                final Initiator[] initiators = new Initiator[] {(Initiator) documentListener};
                final Commit setTextCommit = SetTextFactory.create(name, textInputOutputECD, jTextFieldBB, initiators);

                jTextFieldBB.addDocumentListenerToBoth(documentListener);
                jTextFieldBB.getBranch().add(setTextCommit);
            }

            return jTextFieldBB;
        }
    }
}
