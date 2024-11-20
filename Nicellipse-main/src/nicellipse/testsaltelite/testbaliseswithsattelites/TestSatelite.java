package nicellipse.testsaltelite.testbaliseswithsattelites;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;
import nicellipse.testsaltelite.balises.BaliseModel;
import nicellipse.testsaltelite.balises.BaliseView;
import nicellipse.testsaltelite.sattelite.SatteliteModel;
import nicellipse.testsaltelite.sattelite.SatteliteView;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class TestSatelite {
    public static void main(String[] args) {
        NiSpace space = new NiSpace("Mobi 1", new Dimension(400, 400));
        // Partie blanche
        NiRectangle topContainer = new NiRectangle();
        topContainer.setBackground(Color.white);
        topContainer.setSize(new Dimension(400, 200));
        space.add(topContainer);

        // Partie bleue
        NiRectangle bottomContainer = new NiRectangle();
        bottomContainer.setBackground(Color.blue);
        bottomContainer.setSize(new Dimension(400, 200));
        bottomContainer.setLocation(0, 200);
        space.add(bottomContainer);

        SatteliteModel satellite1 = new SatteliteModel(0, 20);
        satellite1.configureToMoveToRight();
        SatteliteView satelliteV1 = new SatteliteView(satellite1);
        SatteliteModel satellite2 = new SatteliteModel(0, 30);
        satellite2.configureToMoveToRight();
        SatteliteView satelliteV2 = new SatteliteView(satellite2);

        // Create 4 balises model with 4 random positions limited in height and width of the container
        BaliseModel baliseModel1 = new BaliseModel((int) (Math.random() * 400), (int) (Math.random() * 400), 0, 400, 200);
        BaliseModel baliseModel2 = new BaliseModel((int) (Math.random() * 400), (int) (Math.random() * 400), 0, 400, 200);
        BaliseModel baliseModel3 = new BaliseModel((int) (Math.random() * 400), (int) (Math.random() * 400), 0, 400, 200);
        BaliseModel baliseModel4 = new BaliseModel((int) (Math.random() * 400), (int) (Math.random() * 400), 0, 400, 200);

        // Create balise views and register each to its balise model
        BaliseView baliseView1 = new BaliseView(baliseModel1);
        BaliseView baliseView2 = new BaliseView(baliseModel2);
        BaliseView baliseView3 = new BaliseView(baliseModel3);
        BaliseView baliseView4 = new BaliseView(baliseModel4);

        // Add balises to the bottom container
        bottomContainer.add(baliseView1);
        bottomContainer.add(baliseView2);
        bottomContainer.add(baliseView3);
        bottomContainer.add(baliseView4);

        topContainer.add(satelliteV1);
        topContainer.add(satelliteV2);

        space.openInWindow();

        while (true) {
            final Runnable doit = new Runnable() {
                public void run() {
                    satellite1.moveBy(2);
                    satellite2.moveBy(1);
                    baliseModel1.move();
                    baliseModel2.move();
                    baliseModel3.move();
                    baliseModel4.move();
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            launch(doit);
        }
    }

    public static void launch(Runnable r) {
        try {
            SwingUtilities.invokeAndWait(r);
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
