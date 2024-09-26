package tfw.awt.graphic;

import java.awt.Graphics2D;

public final class DrawLineGraphic {
    private DrawLineGraphic() {}

    public static Graphic create(Graphic graphic, int x1, int y1, int x2, int y2) {
        return new GraphicImpl1(graphic, x1, y1, x2, y2);
    }

    private static class GraphicImpl1 implements Graphic {
        private final Graphic graphic;
        private final int x1;
        private final int y1;
        private final int x2;
        private final int y2;

        private GraphicImpl1(Graphic graphic, int x1, int y1, int x2, int y2) {
            this.graphic = graphic;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        @Override
        public void paint(Graphics2D graphics2D) {
            if (graphic != null) {
                graphic.paint(graphics2D);
            }

            graphics2D.drawLine(x1, y1, x2, y2);
        }
    }

    public static Graphic create(Graphic graphic, int[] x1s, int[] y1s, int[] x2s, int[] y2s) {
        return new GraphicImpl2(graphic, x1s, y1s, x2s, y2s);
    }

    private static class GraphicImpl2 implements Graphic {
        private final Graphic graphic;
        private final int[] x1s;
        private final int[] y1s;
        private final int[] x2s;
        private final int[] y2s;

        private GraphicImpl2(Graphic graphic, int[] x1s, int[] y1s, int[] x2s, int[] y2s) {
            this.graphic = graphic;
            this.x1s = x1s;
            this.y1s = y1s;
            this.x2s = x2s;
            this.y2s = y2s;
        }

        @Override
        public void paint(Graphics2D graphics2D) {
            if (graphic != null) {
                graphic.paint(graphics2D);
            }
            for (int i = 0; i < x1s.length; i++) {
                graphics2D.drawLine(x1s[i], y1s[i], x2s[i], y2s[i]);
            }
        }
    }
}
