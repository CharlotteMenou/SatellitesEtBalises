package nicellipse.testsaltelite.balises;

import nicellipse.testsaltelite.announcer.Announcer;

import javax.swing.*;
import java.awt.*;

public class BaliseView extends JPanel {
    private BaliseModel model;
    private Announcer announcer;

    // additional attributes
    private boolean isOnTop = false;
    private boolean isFree = true;


    public BaliseView(BaliseModel model) {
        this.model = model;
        this.model.register(this);
        this.setSize(10, 10);
        this.setBackground(Color.yellow);
        this.setLocation(model.getX(), model.getY());
        this.announcer = new Announcer();
    }

    // add the register method
    public void register(Object o) {
        this.announcer.register(o, ListenToSatteliteEvent.class);
    }


    public void onBaliseMove(MoveBaliseEvent event) {
        BaliseModel model = (BaliseModel) event.getSource();
        this.setLocation(model.getX(), model.getY());
        // announce the movement to sattelites
        this.announcer.announce(new ListenToSatteliteEvent(this));

        // check if is on top,  then set the color green and set isOnTop to true
        if (this.getY() < 10) {
            this.setBackground(Color.green);
            isOnTop = true;
        } else {
            this.setBackground(Color.yellow);
            isOnTop = false;
        }
        this.repaint();
    }

    public boolean isOnTop() {
        return this.isOnTop;
    }

    public boolean isFree() {
        return this.isFree;
    }
    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }
}