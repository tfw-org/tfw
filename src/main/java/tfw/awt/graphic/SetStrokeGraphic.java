package tfw.awt.graphic;

import java.awt.Graphics2D;
import java.awt.Stroke;

public final class SetStrokeGraphic {
    private SetStrokeGraphic() {}

    public static Graphic create(Graphic graphic, Stroke stroke) {
        return new GraphicImpl(graphic, stroke);
    }

    private static class GraphicImpl implements Graphic {
        private final Graphic graphic;
        private final Stroke stroke;

        private GraphicImpl(Graphic graphic, Stroke stroke) {
            this.graphic = graphic;
            this.stroke = stroke;
        }

        @Override
        public void paint(Graphics2D graphics2D) {
            if (graphic != null) {
                graphic.paint(graphics2D);
            }
            graphics2D.setStroke(stroke);
        }
    }
}
