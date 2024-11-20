package nicellipse.testsaltelite.testbaliseswithsattelites;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;
import nicellipse.testsaltelite.balises.BaliseModel;
import nicellipse.testsaltelite.balises.BaliseView;
import nicellipse.testsaltelite.sattelite.SatteliteModel;
import nicellipse.testsaltelite.sattelite.SatteliteView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class TestAuto {

    // Load the file Sattelite.png and create a new File object

    static File baliseImage1 = new File("Balise.png");
    static File satteliteImage1 = new File("Sattelite.png");
    static File satteliteImage2 = new File("Sattelite1.png");

        static BufferedImage rawBalise1;

    static {
        try {
            rawBalise1 = ImageIO.read(baliseImage1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static BufferedImage rawBalise2;

    static {
        try {
            rawBalise2 = ImageIO.read(baliseImage1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static BufferedImage rawSattelite1;

    static {
        try {
            rawSattelite1 = ImageIO.read(satteliteImage1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static BufferedImage rawSattelite2;

    static {
        try {
            rawSattelite2 = ImageIO.read(satteliteImage2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // transform into buffered images


    static HashMap<String,BaliseModel> LMbalises;
    static HashMap<String,SatteliteModel> LMsattelites;
    static HashMap<String,BaliseView> LVbalises;
    static HashMap<String,SatteliteView> LVsattelites;

    static NiSpace space = new NiSpace("Mobi 1", new Dimension(400, 400));

    // Partie blanche
    static NiRectangle topContainer = new NiRectangle();

    // Partie bleue
    static NiRectangle bottomContainer = new NiRectangle();

    public TestAuto() throws IOException {
    }

    public static void main(String[] args) throws IOException {

        topContainer.setBackground(Color.white);
        topContainer.setSize(new Dimension(400, 200));
        space.add(topContainer);

        bottomContainer.setBackground(Color.blue);
        bottomContainer.setSize(new Dimension(400, 200));
        bottomContainer.setLocation(0, 200);
        space.add(bottomContainer);

         LMbalises = new HashMap<String,BaliseModel>();
        LMsattelites = new HashMap<String,SatteliteModel>();
        LVbalises = new HashMap<String,BaliseView>();
        LVsattelites = new HashMap<String,SatteliteView>();

    // Création et configuration du satellite
   /* SatteliteModel satellite1 = new SatteliteModel(0, 20);
        satellite1.configureToMoveToRight();
    SatteliteView satelliteV2 = new SatteliteView(satellite1);*/

        // Scanner pour lire les lignes du terminal
        Scanner scanner = new Scanner(System.in);

        space.openInWindow();
        // Instructions dans la boucle
        Thread moveThread = new Thread(() -> {
            while (true) {
                final Runnable doit = new Runnable() {
                    public void run() {
                        for (SatteliteModel sat : LMsattelites.values()) {
                            if(sat != null){
                                sat.moveBy(1);
                            }
                        }
                        for (BaliseModel bal : LMbalises.values()) {
                            if( bal != null){
                                bal.move();
                            }
                        }
                    }
                };
                launch(doit);

                try {
                    Thread.sleep(20); // Pause de 20ms entre chaque mise à jour
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        moveThread.start();

        // Instructions dans la boucle
        while (true) {
            System.out.println("En attente d'une commande : ");
            String input = scanner.nextLine().trim();
            interpret(input);  // Interpréter la ligne
        }
    }

    // Méthode pour interpréter la ligne d'entrée du terminal
    public static void interpret(String line) throws IOException {
        if (line.contains(":=")) {
            handleAssignment(line);
        } else if (line.contains(".")) {
            handleMethodCall(line);
        }
    }

    public static void handleMethodCall(String line) throws IOException {
        String[] parts = line.split("\\.");
        String varName = parts[0].trim();
        String methodCall = parts[1].trim();
        SatteliteModel objSat = LMsattelites.get(varName);
        BaliseModel objBal =  LMbalises.get(varName);

        if (objBal != null && methodCall.startsWith("changeDeplacement")) {
            // Extraire le type de déplacement entre les parenthèses
            String deplacement = methodCall.substring(methodCall.indexOf("(") + 2, methodCall.lastIndexOf(")") - 1);
            switch (deplacement.toLowerCase()) {
                case "hor":
                    objBal.setTypeMove(0); // 0 pour déplacement horizontal
                    break;
                case "vert":
                    objBal.setTypeMove(1); // 1 pour déplacement vertical
                    break;
                case "sin":
                    objBal.setTypeMove(2); // 2 pour déplacement diagonal
                    break;
            }
            return;
        }

        if (objSat != null && methodCall.contains("start")) {
            SatteliteView satv = new SatteliteView(objSat,satteliteImage1, rawSattelite1, rawSattelite2);
            LVsattelites.put(varName,satv);
            topContainer.add(satv);
            register(satv);
        } else if (objBal != null && methodCall.contains("start")) {
            BaliseView balv = new BaliseView(objBal, baliseImage1, rawBalise1, rawBalise2);
            LVbalises.put(varName,balv);
            bottomContainer.add(balv);
            register(objBal);
        }
        if(objSat != null && methodCall.contains("stop")){
            topContainer.remove(LVsattelites.get(varName));
            LVsattelites.remove(varName);
        } else if (objBal != null && methodCall.contains("stop")) {
            bottomContainer.remove(LVbalises.get(varName));
            LVbalises.remove(varName);
        }
        if (objSat != null && methodCall.contains("delete")) {
            topContainer.remove(LVsattelites.get(varName));
            LMsattelites.remove(varName);
            LVsattelites.remove(varName);
        } else if (objBal != null && methodCall.contains("delete")) {
            LMbalises.remove(varName);
            LVbalises.remove(varName);
            bottomContainer.remove(LVbalises.get(varName));
        }
    }

    // Gérer une assignation d'objet (ex: sat1 := new Satellite(hauteur = 2000))
    public static void handleAssignment(String line) {
        String[] parts = line.split(":=");
        String varName = parts[0].trim();
        String objectCreation = parts[1].trim();
        if (objectCreation.startsWith("new")) {
            String className = objectCreation.split(" ")[1];
            if(className.contains("Satellite")){
                String params = objectCreation.substring(objectCreation.indexOf("(") + 1, objectCreation.indexOf(")"));
                SatteliteModel sat = new SatteliteModel(Integer.valueOf(params.split(",")[0]), Integer.valueOf(params.split(",")[1]));
                sat.configureToMoveToRight();
                LMsattelites.put(varName,sat);
            }
            if(className.contains("Balise")){
                String params = objectCreation.substring(objectCreation.indexOf("(") + 1, objectCreation.indexOf(")"));
               BaliseModel bal = new BaliseModel(Integer.valueOf(params.split(",")[0]), Integer.valueOf(params.split(",")[1]), 0, 400, 200);
               LMbalises.put(varName,bal);
            }
        }
    }

    public static void register(SatteliteView sattelite){

        sattelite.registerAll(new java.util.ArrayList<BaliseModel>() {
            {
                LMbalises.forEach((key, balise) ->
                        add(balise)  );
            }
        });
    }

    public static void register(BaliseModel balise) {
        LVsattelites.forEach((key, satellite) -> {
            satellite.registerAll(new ArrayList<>() {{
                add(balise);
            }});
        });
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
