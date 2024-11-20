package nicellipse.testsaltelite.balises;

import nicellipse.component.NiImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BaliseView extends NiImage {
    private BaliseModel model;
    private BufferedImage rawImage1;
    private BufferedImage rawImage2;

    public BaliseView(BaliseModel model, File filePath, BufferedImage rawImage1, BufferedImage rawImage2) throws IOException {
        super(filePath);
        this.rawImage1 = rawImage1;
        this.rawImage2 = rawImage2;
        this.model = model;
        this.model.register(this);
        this.setLocation(model.getX(), model.getY());
    }

    public void onBaliseMove(MoveBaliseEvent event) {
        BaliseModel model = (BaliseModel) event.getSource();
        this.setLocation(model.getX(), model.getY());

        // check if is on top, then set the color green and set isOnTop to true
        if (this.getY() < 10) {
           this.setImage(rawImage2);
        } else {
            this.setImage(rawImage1);
        }
        this.repaint();
    }
}