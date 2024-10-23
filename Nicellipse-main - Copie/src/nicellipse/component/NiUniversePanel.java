package nicellipse.component;

import nicellipse.component.NiSpace;

import java.awt.*;


public class NiUniversePanel extends NiSpace {

    public NiUniversePanel(String name, Dimension dim) {
        super(name, dim);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight() / 2);

        g.setColor(Color.BLUE);
        g.fillRect(0, getHeight() / 2, getWidth(), getHeight());
    }
}