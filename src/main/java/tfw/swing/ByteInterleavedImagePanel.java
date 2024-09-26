package tfw.swing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import tfw.awt.ecd.ColorModelECD;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaUtil;
import tfw.immutable.ilm.byteilm.ByteIlm;
import tfw.tsm.Branch;
import tfw.tsm.Commit;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ilm.ByteIlmECD;

public class ByteInterleavedImagePanel extends JPanelBB {
    private static final long serialVersionUID = 1L;
    private int x = 0;
    private int y = 0;
    private BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

    public ByteInterleavedImagePanel(
            String name, IntegerECD xECD, IntegerECD yECD, ByteIlmECD byteIlmECD, ColorModelECD colorModelECD) {
        super(new Branch("ByteInterleavedImagePanel[" + name + "]"));

        getBranch().add(new ImagePanelCommit(xECD, yECD, byteIlmECD, colorModelECD));

        setOpaque(false);
    }

    private class ImagePanelCommit extends Commit {
        private final IntegerECD xECD;
        private final IntegerECD yECD;
        private final ByteIlmECD byteIlmECD;
        private final ColorModelECD colorModelECD;

        public ImagePanelCommit(IntegerECD xECD, IntegerECD yECD, ByteIlmECD byteIlmECD, ColorModelECD colorModelECD) {
            super("ByteInterleavedImagePanelCommit", new ObjectECD[] {xECD, yECD, byteIlmECD, colorModelECD});

            this.xECD = xECD;
            this.yECD = yECD;
            this.byteIlmECD = byteIlmECD;
            this.colorModelECD = colorModelECD;
        }

        @Override
        protected void commit() {
            ByteIlm byteIlm = (ByteIlm) get(byteIlmECD);
            int width;
            int height;
            try {
                width = (int) byteIlm.width();
                height = (int) byteIlm.height();
            } catch (IOException e) {
                return;
            }
            ColorModel colorModel = (ColorModel) get(colorModelECD);
            //			ByteIla byteIla = ByteIlaFromByteIlm.create(byteIlm);
            ByteIla byteIla = null;

            DataBufferByte dbb = null;

            try {
                dbb = new DataBufferByte(ByteIlaUtil.toArray(byteIla), (int) byteIla.length());
            } catch (IOException e) {
                return;
            }
            WritableRaster wr = Raster.createInterleavedRaster(dbb, width, height, width, 1, new int[] {0}, null);

            synchronized (ByteInterleavedImagePanel.this) {
                x = ((Integer) get(xECD)).intValue();
                y = ((Integer) get(yECD)).intValue();
                image = new BufferedImage(colorModel, wr, false, null);
            }

            repaint();
        }
    }

    @Override
    public final void paint(Graphics g) {
        synchronized (this) {
            g.drawImage(image, x, y, this);
        }
    }
}
