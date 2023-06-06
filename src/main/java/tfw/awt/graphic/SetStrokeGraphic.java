package tfw.awt.graphic;

import java.awt.Graphics2D;
import java.awt.Stroke;

public final class SetStrokeGraphic {
    private SetStrokeGraphic() {}

    public static Graphic create(Graphic graphic, Stroke stroke) {
        return (new MyGraphic(graphic, stroke));
    }

    private static class MyGraphic implements Graphic {
        private final Graphic graphic;
        private final Stroke stroke;

        public MyGraphic(Graphic graphic, Stroke stroke) {
            this.graphic = graphic;
            this.stroke = stroke;
        }

        public void paint(Graphics2D graphics2D) {
            if (graphic != null) {
                graphic.paint(graphics2D);
            }
            graphics2D.setStroke(stroke);
        }
    }
}
