package tfw.awt.graphic;

import java.awt.Color;
import java.awt.Graphics2D;

public final class SetColorGraphic {
    private SetColorGraphic() {}

    public static Graphic create(Graphic graphic, Color color) {
        return (new MyGraphic(graphic, color));
    }

    private static class MyGraphic implements Graphic {
        private final Graphic graphic;
        private final Color color;

        public MyGraphic(Graphic graphic, Color color) {
            this.graphic = graphic;
            this.color = color;
        }

        public void paint(Graphics2D graphics2D) {
            if (graphic != null) {
                graphic.paint(graphics2D);
            }
            graphics2D.setColor(color);
        }
    }
}
