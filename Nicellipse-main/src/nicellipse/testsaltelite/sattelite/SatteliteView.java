package nicellipse.testsaltelite.sattelite;

import nicellipse.component.NiImage;
import nicellipse.testsaltelite.balises.BaliseModel;
import nicellipse.testsaltelite.balises.ListenToSatteliteEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SatteliteView extends NiImage {
    private static final long serialVersionUID = -8719870900105867735L;
    Integer handCheckCount;
    Color color;
    private SatteliteModel model;
    private BaliseModel balise;
    private ArrayList<BaliseModel> balises = new ArrayList<>();
    private boolean isConnected = false;
    // images attributes
    private BufferedImage rawImage1;
    private BufferedImage rawImage2;

    public SatteliteView(SatteliteModel model, File filePath, BufferedImage rawImage1, BufferedImage rawImage2) throws IOException {
        super(filePath);
        this.rawImage1 = rawImage1;
        this.rawImage2 = rawImage2;
        this.handCheckCount = 0;
        this.model = model;
        this.model.register(this);
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
                this.setImage(rawImage2);
                this.repaint();
            } else {
                this.balise.setFree(true);
                this.balise = null;
                this.isConnected = false; // set to false
                this.setImage(rawImage1);
                // check size
                System.out.println("Size of this : " + this.getSize());
                this.repaint();
            }
        } else {
            this.isConnected = false; // set to false
            this.setImage(rawImage1);
            this.repaint();
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
                        this.setImage(rawImage2);
                        this.repaint();
                    } else {
                        this.isConnected = false;
                        this.balise.setFree(true);
                        this.balise = null;
                        this.setImage(rawImage1);
                        this.repaint();
                    }
                } else {
                    this.isConnected = false;
                    this.balise.setFree(true);
                    this.balise = null;
                    this.setImage(rawImage1);
                    this.repaint();
                }
            }
        }
    }
}
