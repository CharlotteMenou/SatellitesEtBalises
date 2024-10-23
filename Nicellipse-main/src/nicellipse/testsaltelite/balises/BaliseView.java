package nicellipse.testsaltelite.balises;

import javax.swing.*;
import java.awt.*;

public class BaliseView extends JPanel {
    private BaliseModel model;

    public BaliseView(BaliseModel model) {
        this.model = model;
        this.model.register(this);
        this.setSize(6, 6);
        this.setBackground(Color.red);
        this.setLocation(model.getX(), model.getY());
    }

    public void onBaliseMove(MoveBaliseEvent event) {
        BaliseModel model = (BaliseModel) event.getSource();
         this.setLocation(model.getX(), model.getY());
            this.repaint();
    }
}