package tfw.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import tfw.awt.ecd.GraphicECD;
import tfw.awt.graphic.Graphic;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Commit;
import tfw.tsm.MultiplexedBranch;
import tfw.tsm.MultiplexedBranchFactory;
import tfw.tsm.TreeComponent;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class PlotPanel extends JPanel implements BranchBox {
    private static final long serialVersionUID = 1L;
    private static final GraphicECD GRAPHIC_ECD = new GraphicECD("graphic");
    private static final ObjectIlaECD MULTI_GRAPHIC_ECD = new ObjectIlaECD("multiGraphic");

    private final Branch branch;
    private final MultiplexedBranch multiplexedBranch;

    private ObjectIla<Object> multiGraphic = null;

    public PlotPanel(String name) {
        this(new Branch("PlotPanel[" + name + "]"));
    }

    public PlotPanel(Branch branch) {
        this.branch = branch;

        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
        mbf.addMultiplexer(GRAPHIC_ECD, MULTI_GRAPHIC_ECD);
        multiplexedBranch = mbf.create("PlotPanelPainters");
        branch.add(multiplexedBranch);

        PlotPanelCommit plotPanelCommit = new PlotPanelCommit(this);
        branch.add(plotPanelCommit);
    }

    private final class PlotPanelCommit extends Commit {
        private final PlotPanel plotPanel;

        public PlotPanelCommit(PlotPanel plotPanel) {
            super("PlotPanelCommit", new ObjectECD[] {MULTI_GRAPHIC_ECD}, null, null);

            this.plotPanel = plotPanel;
        }

        @SuppressWarnings("unchecked")
        protected void commit() {
            synchronized (plotPanel) {
                multiGraphic = (ObjectIla<Object>) get(MULTI_GRAPHIC_ECD);
                plotPanel.repaint();
            }
        }
    }

    public final void paint(Graphics graphics) {
        Object[] mg = null;

        synchronized (this) {
            if (multiGraphic == null) {
                return;
            }
            try {
                mg = new Object[(int) multiGraphic.length()];

                multiGraphic.toArray(mg, 0, 0, mg.length);
            } catch (DataInvalidException die) {
                return;
            }
        }

        for (int i = 0; i < mg.length; i++) {
            Graphic g = (Graphic) mg[i];
            if (g != null) {
                g.paint((Graphics2D) graphics.create());
            }
        }
    }

    public final void addComponentListenerToBoth(ComponentListener listener) {
        addComponentListener(listener);
        branch.add((TreeComponent) listener);
    }

    public final void removeComponentListenerFromBoth(ComponentListener listener) {
        removeComponentListener(listener);
        branch.remove((TreeComponent) listener);
    }

    public final void addMouseListenerToBoth(MouseListener listener) {
        addMouseListener(listener);
        branch.add((TreeComponent) listener);
    }

    public final void removeMouseListenerFromBoth(MouseListener listener) {
        removeMouseListener(listener);
        branch.remove((TreeComponent) listener);
    }

    public final void addMouseMotionListenerToBoth(MouseMotionListener listener) {
        addMouseMotionListener(listener);
        branch.add((TreeComponent) listener);
    }

    public final void removeMouseMotionListenerFromBoth(MouseMotionListener listener) {
        removeMouseMotionListener(listener);
        branch.remove((TreeComponent) listener);
    }

    public final void addGraphicProducer(TreeComponent treeComponent, int multiplexIndex) {
        multiplexedBranch.add(treeComponent, multiplexIndex);
    }

    public final void removeGraphicProducer(TreeComponent treeComponent) {
        multiplexedBranch.remove(treeComponent);
    }

    public final Branch getBranch() {
        return branch;
    }
}
