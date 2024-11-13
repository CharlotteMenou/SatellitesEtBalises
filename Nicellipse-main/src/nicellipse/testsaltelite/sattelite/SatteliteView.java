package nicellipse.testsaltelite.sattelite;

import nicellipse.component.NiRectangle;
import nicellipse.testsaltelite.*;
import nicellipse.testsaltelite.announcer.AbstractEvent;
import nicellipse.testsaltelite.announcer.Announcer;
import nicellipse.testsaltelite.balises.BaliseView;
import nicellipse.testsaltelite.balises.ListenToSatteliteEvent;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SatteliteView extends NiRectangle {
    private static final long serialVersionUID = -8719870900105867735L;
    final Integer handCheckGap = 20;
    final Integer maxHandChecks = 1000;
    Integer handCheckCount;
    Color color;
    private SatteliteModel model;
    private Announcer announcer;
    private BaliseView baliseView;
    // dont move attribute
    private boolean dontMove = false;

    public SatteliteView(SatteliteModel model) {
        this.color = Color.red;
        this.handCheckCount = 0;
        this.announcer = new Announcer();
        this.model = model;
        this.model.register(this);
        this.setBackground(color);
        this.setSize(new Dimension(20, 20));
        this.setLocation(model.getX(), model.getY());
    }

    public SatteliteView(SatteliteModel model, BaliseView balise) {
        this.color = Color.red;
        this.handCheckCount = 0;
        this.announcer = new Announcer();
        this.model = model;
        this.model.register(this);
        this.setBackground(color);
        this.setSize(new Dimension(20, 20));
        this.setLocation(model.getX(), model.getY());
        // register this to balises
        this.baliseView = balise;
        this.baliseView.register(this);
    }

    public void onSatteliteMove(SatteliteMoveEvent event) {
        if (dontMove) {
            // set location from baliseview
            this.setLocation(baliseView.getLocation().x, baliseView.getLocation().y - 20);
            this.repaint();

        } else {
            SatteliteModel model = (SatteliteModel) event.getSource();
            // set location of satteliteview : if x is out of range of parent, go back to
            if (this.getLocation().x >= this.getParent().getWidth()) {
                this.setLocation(model.getX()% this.getParent().getWidth(),  model.getY());
            } else {
                this.setLocation(model.getX(), model.getY());
            }
            this.repaint();
        }
    }

    public void onSatteliteStop(ListenToSatteliteEvent event) {
        BaliseView baliseView = (BaliseView) event.getSource();
        // debug
        System.out.println("SatteliteView.onSatteliteStop: " + this.getLocation().x + " " + baliseView.getLocation().x);
        System.out.println("Is the balise on top? " + baliseView.isOnTop());
        // if the sattelite coordinates are in the same range of balise coordinates
        if (baliseView.isOnTop() && this.getLocation().x >= baliseView.getLocation().x - 10 && this.getLocation().x <= baliseView.getLocation().x + 10) {
            // stop the sattelite
            this.dontMove = true;
            // set background to black
            this.setBackground(Color.black);
            // set location of baliseView
            this.setLocation(baliseView.getLocation().x, this.getY());
            // debug
            System.out.println("SatteliteView.onSatteliteStop: " + this.getLocation().x + " " + baliseView.getLocation().x);
        }
    }

}
