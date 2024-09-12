package tfw.awt.graphic;

import java.awt.Graphics2D;

public final class DrawStringGraphic {
    private DrawStringGraphic() {}

    public static Graphic create(Graphic graphic, String string, int x, int y) {
        return new GraphicImpl(graphic, string, x, y);
    }

    private static class GraphicImpl implements Graphic {
        private final Graphic graphic;
        private final String string;
        private final int x;
        private final int y;

        private GraphicImpl(Graphic graphic, String string, int x, int y) {
            this.graphic = graphic;
            this.string = string;
            this.x = x;
            this.y = y;
        }

        public void paint(Graphics2D graphics2D) {
            if (graphic != null) {
                graphic.paint(graphics2D);
            }

            graphics2D.drawString(string, x, y);
        }
    }

    public static Graphic create(Graphic graphic, String[] strings, int[] xs, int[] ys) {
        return new GraphicImpl1(graphic, strings, xs, ys);
    }

    private static class GraphicImpl1 implements Graphic {
        private final Graphic graphic;
        private final String[] strings;
        private final int[] xs;
        private final int[] ys;

        private GraphicImpl1(Graphic graphic, String[] strings, int[] xs, int[] ys) {
            this.graphic = graphic;
            this.strings = strings;
            this.xs = xs;
            this.ys = ys;
        }

        public void paint(Graphics2D graphics2D) {
            if (graphic != null) {
                graphic.paint(graphics2D);
            }
            for (int i = 0; i < strings.length; i++) {
                graphics2D.drawString(strings[i], xs[i], ys[i]);
            }
        }
    }
}
