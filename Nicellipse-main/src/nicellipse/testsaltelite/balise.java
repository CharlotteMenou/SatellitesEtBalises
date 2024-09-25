package nicellipse.testsaltelite;

import nicellipse.component.NiBalise;
import nicellipse.component.NiSpace;
import nicellipse.component.NiBalise;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class balise {

    NiSpace space = new NiSpace("haut bas", new Dimension(500, 500));
    NiSpace space1 = new NiSpace("haut bas", new Dimension(500, 500));

    NiBalise robi = new NiBalise(1);
    NiBalise robi1 = new NiBalise(3);
    NiBalise robi2 = new NiBalise(5);
    Thread thread1 = new Thread();
    Thread thread2 = new Thread();
    Thread thread3 = new Thread();
    void moveRobi(int x, int y, NiBalise robot) {
        final Runnable doit = new Runnable() {
            public void run() {
                robot.setLocation(new Point(x, y));
                try {
                    Thread.sleep(50);
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
    void placement_debut(NiBalise robi){
        Random random = new Random();
        robi.setBackground(Color.red);
        robi.setSize(20, 20);
        int y = random.nextInt(500);
        robi.setLocation(0,y);
        System.out.println(0 + " "+ y);
    }


    void moveBalise(NiBalise robi, int speed, int mode) {
        int x = robi.getX();
        int y = robi.getY();

        boolean movingRight = true, movingDown = true;
        int amplitude = 50; // Amplitude du mouvement sinusoïdal
        double angle = 0;

        long startTime = System.currentTimeMillis();

        while (true) {

            long elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime % 5 == 0){
                robi.memoire.add(String.valueOf(elapsedTime));
            }

            if(robi.isFull()){
                while (y < space.getHeight() - robi.getHeight()) {
                    y = y + speed;
                }
            }
            else {
                switch (mode) {
                    case 0:
                        // Déplacement horizontal uniquement
                        x += movingRight ? speed : -speed;
                        if (x <= 0 || x >= space.getWidth() - robi.getWidth()) {
                            movingRight = !movingRight;
                        }
                        break;

                    case 1:
                        // Déplacement vertical uniquement
                        y += movingDown ? speed : -speed;
                        if (y <= 0 || y >= space.getHeight() - robi.getHeight()) {
                            movingDown = !movingDown;
                        }
                        break;

                    case 2:
                        // Déplacement horizontal avec mouvement sinusoïdal en Y
                        x += movingRight ? speed : -speed;
                        angle += 0.1; // Incrémente l'angle pour le mouvement sinusoïdal
                        y = (int) (space.getHeight() / 2 + amplitude * Math.sin(angle)); // Mouvement sinusoïdal

                        if (x <= 0 || x >= space.getWidth() - robi.getWidth()) {
                            movingRight = !movingRight;
                        }
                        break;
                }
            }
            this.moveRobi(x, y, robi);

        }
    }

    public balise() {
        ArrayList<NiBalise> robots = new ArrayList<>();
        space.setBackground(Color.pink);
        robots.add(robi);
        robots.add(robi1);
        robots.add(robi2);
        for(NiBalise r: robots){
            placement_debut(r);
            space.add(r);
        }
        space.openInWindow();

        thread1 = new Thread(() -> moveBalise(robi,5,0));
        thread2 = new Thread(() -> moveBalise(robi1,5,1));
        thread3 = new Thread(() -> moveBalise(robi2,1,2));

        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static void main(String[] args) {
        new balise();
    }

}

