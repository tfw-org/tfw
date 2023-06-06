package tfw.visualizer;

import java.awt.EventQueue;
import java.awt.Frame;
import tfw.awt.event.WindowInitiator;
import tfw.swing.JDialogBB;
import tfw.swing.JPanelBB;
import tfw.tsm.Branch;
import tfw.tsm.BranchFactory;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.StatelessTriggerECD;

public class ShowPanelInNonModalDialog extends Branch {
    private static final StatelessTriggerECD WINDOW_CLOSING_ECD =
            new StatelessTriggerECD("SPINMD_windowClosingTrigger");

    private JDialogBB dialog = null;

    public ShowPanelInNonModalDialog(StatelessTriggerECD triggerECD, Frame owner, String title, Class jPanelBBClass) {
        super("ShowPanelInNonModalDialog");

        add(new MyConverter(triggerECD, owner, title, jPanelBBClass));
    }

    private class MyCommit extends TriggeredCommit {
        public MyCommit(StatelessTriggerECD triggerECD) {
            super("ShowPanelInMocalDialogCommit", triggerECD, null, null);
        }

        protected void commit() {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    if (dialog != null) {
                        remove(dialog.getBranch());
                        dialog.dispose();

                        dialog = null;
                    }
                }
            });
        }
    }

    private class MyConverter extends TriggeredConverter {
        private final Frame owner;
        private final String title;
        private final Class jPanelBBClass;

        public MyConverter(StatelessTriggerECD triggerECD, Frame owner, String title, Class jPanelBBClass) {
            super("ShowPanelInNonModalDialogConverter", triggerECD, null, null);

            this.owner = owner;
            this.title = title;
            this.jPanelBBClass = jPanelBBClass;
        }

        protected void convert() {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    if (dialog == null) {
                        JPanelBB contentPane;
                        try {
                            contentPane = (JPanelBB) jPanelBBClass.newInstance();
                        } catch (Exception e) {
                            throw new RuntimeException("Cannot create JPanelBB class", e);
                        }

                        BranchFactory branchFactory = new BranchFactory();
                        branchFactory.addEventChannel(WINDOW_CLOSING_ECD);
                        Branch branch = branchFactory.create(
                                "JDialogBB[" + contentPane.getBranch().getName() + "]");

                        dialog = new JDialogBB(branch, owner, title, false);

                        dialog.addWindowListenerToBoth(new WindowInitiator(
                                "ShowPanelInNonModalDialogWI", null, null, WINDOW_CLOSING_ECD, null, null, null, null));
                        dialog.getBranch().add(new MyCommit(WINDOW_CLOSING_ECD));
                        dialog.setContentPaneForBoth(contentPane);

                        add(dialog.getBranch());

                        dialog.pack();
                        dialog.setLocationRelativeTo(owner);
                    }

                    dialog.setVisible(true);
                }
            });
        }
    }
}
