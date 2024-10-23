package nicellipse.testsaltelite.balises;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class balisesTest {

    public static void main(String[] args) {
        NiSpace space = new NiSpace("Mobi 1", new Dimension(400, 400));
        NiRectangle container = new NiRectangle();
        container.setBackground(Color.lightGray);
        container.setSize(new Dimension(400, 400));
        space.add(container);

        // Create 4 balises model with 4 random positions limited in height and width of the container
        BaliseModel baliseModel1 = new BaliseModel((int) (Math.random() * 400), (int) (Math.random() * 400), 0, 400, 400);
        BaliseModel baliseModel2 = new BaliseModel((int) (Math.random() * 400), (int) (Math.random() * 400), 0, 400, 400);
        BaliseModel baliseModel3 = new BaliseModel((int) (Math.random() * 400), (int) (Math.random() * 400), 0, 400, 400);
        BaliseModel baliseModel4 = new BaliseModel((int) (Math.random() * 400), (int) (Math.random() * 400), 0, 400, 400);

        // Create balise views and register each to its balise model
        BaliseView baliseView1 = new BaliseView(baliseModel1);
        BaliseView baliseView2 = new BaliseView(baliseModel2);
        BaliseView baliseView3 = new BaliseView(baliseModel3);
        BaliseView baliseView4 = new BaliseView(baliseModel4);

        container.add(baliseView1);
        container.add(baliseView2);
        container.add(baliseView3);
        container.add(baliseView4);

        space.openInWindow();

        while (true) {
            final Runnable doit = new Runnable() {
                public void run() {
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