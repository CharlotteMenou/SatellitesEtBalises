package nicellipse.testsaltelite.sattelite;

import nicellipse.component.NiRectangle;
import nicellipse.testsaltelite.balises.BaliseView;
import nicellipse.testsaltelite.balises.ListenToSatteliteEvent;

import java.awt.*;
import java.util.ArrayList;

public class SatteliteView extends NiRectangle {
    private static final long serialVersionUID = -8719870900105867735L;
    Integer handCheckCount;
    Color color;
    private SatteliteModel model;
    private BaliseView baliseView;
    private ArrayList<BaliseView> baliseViews = new ArrayList<>();
    // dont move attribute
    private boolean dontMove = false;

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
    public void registerAll(ArrayList<BaliseView> baliseViews) {
        for (BaliseView baliseView : baliseViews) {
            this.baliseViews.add(baliseView);
            baliseView.register(this);
        }
    }

    public void onSatteliteMove(SatteliteMoveEvent event) {
        if (dontMove) {
            // set location from baliseview
            int x = baliseView.getLocation().x % 400;
            this.setLocation(x, this.getY());
            this.repaint();
        } else {
            SatteliteModel model = (SatteliteModel) event.getSource();
            // set location of satteliteview : if x is out of range of parent, go back to 0
            int x = model.getX() % 400;
            this.setLocation(x, this.getY());
            this.repaint();
        }
    }

    public void onSatteliteStop(ListenToSatteliteEvent event) {
        BaliseView baliseView = (BaliseView) event.getSource();
        if (!dontMove) {
            if (baliseView.isFree()) {
                this.baliseView = baliseView;
                // if the sattelite coordinates are in the same range of balise coordinates
                if (baliseView.isOnTop()) {
                    if (this.getLocation().x >= baliseView.getLocation().x - 10 && this.getLocation().x <= baliseView.getLocation().x + 10) {
                        // stop the sattelite
                        this.dontMove = true;
                        // set not free
                        baliseView.setFree(false);
                        // set background to black
                        this.setBackground(Color.black);
                        // set location of baliseView
                        int x = baliseView.getLocation().x % 400;
                        this.setLocation(x, this.getY());
                        this.repaint();
                    }
                }
            }
        }
    }

}
