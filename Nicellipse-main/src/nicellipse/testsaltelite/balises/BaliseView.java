package nicellipse.testsaltelite.balises;

import nicellipse.testsaltelite.announcer.Announcer;

import javax.swing.*;
import java.awt.*;

public class BaliseView extends JPanel {
    private BaliseModel model;

    public BaliseView(BaliseModel model) {
        this.model = model;
        this.model.register(this);
        this.setSize(10, 10);
        this.setBackground(Color.yellow);
        this.setLocation(model.getX(), model.getY());
    }

    public void onBaliseMove(MoveBaliseEvent event) {
        BaliseModel model = (BaliseModel) event.getSource();
        this.setLocation(model.getX(), model.getY());

        // check if is on top,  then set the color green and set isOnTop to true
        if (this.getY() < 10) {
            this.setBackground(Color.green);
        } else {
            this.setBackground(Color.yellow);
        }
        this.repaint();
    }

}