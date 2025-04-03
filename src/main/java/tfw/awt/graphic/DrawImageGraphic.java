package tfw.awt.graphic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public final class DrawImageGraphic {
    private DrawImageGraphic() {}

    public static Graphic create(Graphic graphic, Image img, int x, int y, ImageObserver observer) {
        return new GraphicImpl(graphic, img, x, y, observer);
    }

    private static class GraphicImpl implements Graphic {
        private final Graphic graphic;
        private final Image img;
        private final int x;
        private final int y;
        private final ImageObserver observer;

        private GraphicImpl(Graphic graphic, Image img, int x, int y, ImageObserver observer) {
            this.graphic = graphic;
            this.img = img;
            this.x = x;
            this.y = y;
            this.observer = observer;
        }

        @Override
        public void paint(Graphics2D graphics2D) {
            if (graphic != null) {
                graphic.paint(graphics2D);
            }
            graphics2D.drawImage(img, x, y, observer);
        }
    }
}
