package tfw.awt.graphic;

import java.awt.Graphics2D;

public class EmptyGraphic {
    private EmptyGraphic() {}

    public static Graphic create() {
        return new MyGraphic();
    }

    private static class MyGraphic implements Graphic {
        public MyGraphic() {}

        public void paint(Graphics2D graphics2D) {}
    }
}
