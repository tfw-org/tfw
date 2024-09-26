package tfw.awt.graphic;

import java.awt.Graphics2D;

public class EmptyGraphic {
    private EmptyGraphic() {}

    public static Graphic create() {
        return new GraphicImpl();
    }

    private static class GraphicImpl implements Graphic {
        private GraphicImpl() {}

        @Override
        public void paint(Graphics2D graphics2D) {}
    }
}
