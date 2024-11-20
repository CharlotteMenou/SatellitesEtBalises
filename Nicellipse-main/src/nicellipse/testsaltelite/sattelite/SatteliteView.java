package nicellipse.testsaltelite.sattelite;

import nicellipse.component.NiRectangle;
import nicellipse.testsaltelite.balises.BaliseModel;
import nicellipse.testsaltelite.balises.ListenToSatteliteEvent;

import java.awt.*;
import java.util.ArrayList;

public class SatteliteView extends NiRectangle {
    private static final long serialVersionUID = -8719870900105867735L;
    Integer handCheckCount;
    Color color;
    private SatteliteModel model;
    private BaliseModel balise;
    private ArrayList<BaliseModel> balises = new ArrayList<>();
    private boolean isConnected = false;
    // dont move attribute

    public SatteliteView(SatteliteModel model) {
        this.color = Color.red;
        this.handCheckCount = 0;
        this.model = model;
        this.model.register(this);
        this.setBackground(color);
        this.setSize(new Dimension(20, 20));
        this.setLocation(model.getX(), model.getY());
    }

    // add the register method
    public void registerAll(ArrayList<BaliseModel> balises) {

        for (BaliseModel balise : balises) {
            this.balises.add(balise);
            balise.registerSattelite(this);
        }
    }

    public void onSatteliteMove(SatteliteMoveEvent event) {
        SatteliteModel model = (SatteliteModel) event.getSource();
        // set location of satteliteview : if x is out of range of parent, go back to 0
        int x = model.getX() % 400;
        this.setLocation(x, this.getY());
        if (this.balise != null) {
           // check the position of the balise if is on range
            if (this.getLocation().x >= balise.getX() - 10 && this.getLocation().x <= balise.getX() + 10) {
                this.setBackground(Color.black);
            } else {
                this.balise.setFree(true);
                this.balise = null;
                this.isConnected = false; // set to false
                this.setBackground(Color.red);
            }
        } else {
            this.isConnected = false; // set to false
            this.setBackground(Color.red);
        }
        this.repaint();

    }

    public void onSatteliteStop(ListenToSatteliteEvent event) {
        BaliseModel balise = (BaliseModel) event.getSource();
        if (this.isConnected) {
            // already connected
        } else {
            if (balise.isFree()) {
                this.balise = balise;
                this.isConnected = true;
                // if the sattelite coordinates are in the same range of balise coordinates
                if (balise.isOnTop()) {
                    if (this.getLocation().x >= balise.getX() - 10 && this.getLocation().x <= balise.getX() + 10) {
                        // set not free
                        balise.setFree(false);
                        // set background to black
                        this.setBackground(Color.black);
                        this.repaint();
                    } else {
                        this.isConnected = false;
                        this.balise.setFree(true);
                        this.balise = null;
                        this.setBackground(Color.red);
                        this.repaint();
                    }
                } else {
                    this.isConnected = false;
                    this.balise.setFree(true);
                    this.balise = null;
                    this.setBackground(Color.red);
                    this.repaint();
                }
            }
        }
    }
}
