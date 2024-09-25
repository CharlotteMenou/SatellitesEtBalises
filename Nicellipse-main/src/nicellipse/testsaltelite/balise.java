package nicellipse.testsaltelite;

import nicellipse.component.NiBalise;
import nicellipse.component.NiUniversePanel;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class balise {

    NiUniversePanel space = new NiUniversePanel("Satellites et Balises", new Dimension(300, 300));

    NiBalise robi = new NiBalise(7);
    NiBalise robi1 = new NiBalise(10);
    NiBalise robi2 = new NiBalise(15);
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
        robi.setBackground(Color.yellow);
        robi.setSize(20, 20);
        int y =space.getHeight()/2 + random.nextInt(space.getHeight()/2 - robi.getWidth());
        robi.setLocation(0,y);
        System.out.println(0 + " "+ y);
    }

    void tranfertData (NiBalise robi) throws InterruptedException {
        robi.transfertMemoire();
    }


    void moveBalise(NiBalise robi, int speed, int mode) throws InterruptedException {
        int x = robi.getX();
        int y = robi.getY();

        boolean movingRight = true, movingDown = true;
        int amplitude = 40; // Amplitude du mouvement sinusoïdal
        double angle = 0;

        long startTime = System.currentTimeMillis();

        while (true) {
            robi.setBackground(Color.red);
            long elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime % 25 == 0){
                robi.addToMemoire(String.valueOf(elapsedTime));
                System.out.println(robi.name +" "+ robi.memoire);
            }

            if(robi.isFull() && y!= 1){
                while (y >space.getHeight()/2) {
                    y = y - 1;
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
                        if (y <= space.getHeight()/2 || y >= space.getHeight() - robi.getHeight()) {
                            movingDown = !movingDown;
                        }
                        break;

                    case 2:
                        // Déplacement horizontal avec mouvement sinusoïdal en Y
                        x += movingRight ? speed : -speed;
                        angle += 0.1; // Incrémente l'angle pour le mouvement sinusoïdal
                        int newY = (int) (space.getHeight() / 2 + amplitude * Math.sin(angle)); // Mouvement sinusoïdal

                        if (newY < space.getHeight()/2) {
                            y = space.getHeight()/2; // Fixer Y à 150 si newY dépasse 150
                        } else {
                            y = newY; // Met à jour Y seulement si dans les limites
                        }

                        if (x <= 0 || x >= space.getWidth() - robi.getWidth()) {
                            movingRight = !movingRight;
                        }
                        break;
                }
            }
            this.moveRobi(x, y, robi);
            if(robi.isFull()){
                //tranfertData(robi);
                robi.setBackground(Color.green);
                System.out.println(robi.name + " je vide la mémoire ");
               // Thread.sleep(50);
                System.out.println( robi.name + " "+robi.memoire);
            }

        }
    }

    public balise() {
        ArrayList<NiBalise> robots = new ArrayList<>();
        space.setBackground(Color.pink);
        space.setDimension(new Dimension(300,300));
        robi.name="chacha";
        robi1.name="karibou";
        robi2.name="sardine";
        robots.add(robi);
        robots.add(robi1);
        robots.add(robi2);
        for(NiBalise r: robots){
            placement_debut(r);
            space.add(r);
        }
        space.openInWindow();

        thread1 = new Thread(() -> {
            try {
                moveBalise(robi,5,0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread2 = new Thread(() -> {
            try {
                moveBalise(robi1,5,1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread3 = new Thread(() -> {
            try {
                moveBalise(robi2,1,2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static void main(String[] args) {
        new balise();
    }

}

