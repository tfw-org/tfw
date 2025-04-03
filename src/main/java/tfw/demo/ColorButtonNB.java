package tfw.demo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import tfw.awt.ecd.ColorECD;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;

public class ColorButtonNB extends JButton implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final Branch branch;
    private Color color = null;
    private Color originalColor = null;
    private JDialog dialog = null;
    private JColorChooser colorChooser = null;

    public ColorButtonNB(
            String name,
            final ColorECD colorECD,
            BooleanECD enableECD,
            final String titleText,
            final Component component) {
        String fullName = "ColorButtonNB[" + name + "]";
        branch = new Branch(fullName);

        final Initiator initiator = new Initiator(fullName + "Initiator", new ObjectECD[] {colorECD});

        branch.add(new ColorButtonCommit(fullName, colorECD, enableECD));
        branch.add(initiator);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialog == null) {
                    colorChooser = new JColorChooser();
                    colorChooser.addChooserPanel(new GrayScalePanel());
                    colorChooser.setColor(color);
                    colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            initiator.set(colorECD, colorChooser.getColor());
                        }
                    });
                    dialog = JColorChooser.createDialog(
                            component,
                            titleText,
                            true,
                            colorChooser,
                            new ActionListener() // OK action listener.
                            {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println(colorChooser.getColor());
                                    initiator.set(colorECD, colorChooser.getColor());
                                }
                            },
                            new ActionListener() // Cancel action listener
                            {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    initiator.set(colorECD, originalColor);
                                }
                            });
                }

                originalColor = color;
                dialog.setVisible(true);
            }
        });
        setIcon(new ColorIcon(40, 8));
        setToolTipText("Color Chooser");
        setEnabled(false);
    }

    @Override
    public Branch getBranch() {
        return branch;
    }

    private class ColorButtonCommit extends Commit {
        private final ColorECD colorName;
        private final BooleanECD enableName;

        private ColorButtonCommit(String name, ColorECD colorName, BooleanECD enableName) {
            super(name + "Commit", new ObjectECD[] {colorName, enableName});
            this.colorName = colorName;
            this.enableName = enableName;
        }

        @Override
        protected void commit() {
            color = (Color) get(colorName);
            setEnabled(((Boolean) get(enableName)).booleanValue());
            ColorButtonNB.this.repaint();
        }
    }

    private static class GrayScalePanel extends AbstractColorChooserPanel {
        private static final long serialVersionUID = 1L;
        static final Color[] grays = new Color[256];

        static {
            for (int i = 0; i < 256; i++) {
                grays[i] = new Color(i, i, i);
            }
        }

        private final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);

        public GrayScalePanel() {
            slider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    getColorSelectionModel().setSelectedColor(grays[slider.getValue()]);
                }
            });

            setLayout(new GridLayout(0, 1));
            add(new JLabel("Pick Your Shade of Gray:", JLabel.CENTER));

            JPanel jp = new JPanel();
            jp.add(new JLabel("Black"));
            jp.add(slider);
            jp.add(new JLabel("White"));
            add(jp);
        }

        @Override
        protected void buildChooser() {}

        @Override
        public void updateChooser() {
            slider.setValue(getColorSelectionModel().getSelectedColor().getRed());
        }

        @Override
        public String getDisplayName() {
            return "Gray Scale";
        }

        @Override
        public Icon getSmallDisplayIcon() {
            return null;
        }

        @Override
        public Icon getLargeDisplayIcon() {
            return null;
        }
    }

    private class ColorIcon implements Icon {
        private final int width;
        private final int height;

        public ColorIcon(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fill3DRect(x, y, width, height, true);
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public int getIconHeight() {
            return height;
        }
    }
}
