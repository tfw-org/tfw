package tfw.awt.graphic;

import java.awt.Font;
import java.awt.Graphics2D;

public final class SetFontGraphic {
    private SetFontGraphic() {}

    public static Graphic create(Graphic graphic, Font font) {
        return (new MyGraphic(graphic, font));
    }

    private static class MyGraphic implements Graphic {
        private final Graphic graphic;
        private final Font font;

        public MyGraphic(Graphic graphic, Font font) {
            this.graphic = graphic;
            this.font = font;
        }

        public void paint(Graphics2D graphics2D) {
            if (graphic != null) {
                graphic.paint(graphics2D);
            }
            graphics2D.setFont(font);
        }
    }
}
