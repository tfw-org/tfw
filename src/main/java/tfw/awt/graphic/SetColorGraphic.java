package tfw.awt.graphic;

import java.awt.Color;
import java.awt.Graphics2D;

public final class SetColorGraphic {
    private SetColorGraphic() {}

    public static Graphic create(Graphic graphic, Color color) {
        return new GraphicImpl(graphic, color);
    }

    private static class GraphicImpl implements Graphic {
        private final Graphic graphic;
        private final Color color;

        private GraphicImpl(Graphic graphic, Color color) {
            this.graphic = graphic;
            this.color = color;
        }

        @Override
        public void paint(Graphics2D graphics2D) {
            if (graphic != null) {
                graphic.paint(graphics2D);
            }
            graphics2D.setColor(color);
        }
    }
}
