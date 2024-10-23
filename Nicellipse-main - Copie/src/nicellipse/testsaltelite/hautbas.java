package nicellipse.testsaltelite;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class hautbas {

    NiSpace space = new NiSpace("haut bas", new Dimension(500, 500));
    NiRectangle robi = new NiRectangle();
    NiRectangle robi1 = new NiRectangle();
    NiRectangle robi2 = new NiRectangle();
    void moveRobi(int x, int y, NiRectangle robot) {
        final Runnable doit = new Runnable() {
            public void run() {
                robot.setLocation(new Point(x, y));
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            SwingUtilities.invokeAndWait(doit);
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
    void placement_debut(NiRectangle robi){
        Random random = new Random();
        robi.setBackground(Color.red);
        robi.setSize(20, 20);
        int y = random.nextInt(500);
        robi.setLocation(0,y);
        System.out.println(0 + " "+ y);
    }

    void moveSatelliteHorizontally(NiRectangle robi,int speed){
        int x = robi.getX();
        int y = robi.getY();
        while (true) {
            while (x < space.getWidth() - robi.getWidth()) {
                x = x + speed;
                this.moveRobi(x, y,robi);
            }
            while (x > 0) {
                x = x - speed;
                this.moveRobi(x, y,robi);
            }
        }
    }

    public hautbas() {
        ArrayList<NiRectangle> robots = new ArrayList<>();
        space.setBackground(Color.pink);
        robots.add(robi);
        robots.add(robi1);
        robots.add(robi2);
        for(NiRectangle r: robots){
            placement_debut(r);
            space.add(r);
        }
        space.openInWindow();


        Thread thread1 = new Thread(() -> moveSatelliteHorizontally(robi,1));
        Thread thread2 = new Thread(() -> moveSatelliteHorizontally(robi1,2));
        Thread thread3 = new Thread(() -> moveSatelliteHorizontally(robi2,3));

        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static void main(String[] args) {
        new hautbas();
    }

}

