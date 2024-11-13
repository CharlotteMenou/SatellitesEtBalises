package nicellipse.testsaltelite.testbaliseswithsattelites;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;
import nicellipse.testsaltelite.balises.BaliseModel;
import nicellipse.testsaltelite.balises.BaliseView;
import nicellipse.testsaltelite.sattelite.SatteliteModel;
import nicellipse.testsaltelite.sattelite.SatteliteView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static nicellipse.testsaltelite.Main.launch;

public class TestAuto {

    static HashMap<String,BaliseModel> balises;
    static HashMap<String,SatteliteModel> sattelites;
    static ArrayList<BaliseView> startedB = new ArrayList<BaliseView>();
    static ArrayList<SatteliteView> startedS = new ArrayList<SatteliteView>();

    static NiSpace space = new NiSpace("Mobi 1", new Dimension(400, 400));

    // Partie blanche
    static NiRectangle topContainer = new NiRectangle();

    // Partie bleue
    static NiRectangle bottomContainer = new NiRectangle();

    public static void main(String[] args) {

        topContainer.setBackground(Color.white);
        topContainer.setSize(new Dimension(400, 200));
        space.add(topContainer);

        bottomContainer.setBackground(Color.blue);
        bottomContainer.setSize(new Dimension(400, 200));
        bottomContainer.setLocation(0, 200);
        space.add(bottomContainer);

         balises = new HashMap<String,BaliseModel>();
        sattelites = new HashMap<String,SatteliteModel>();

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
                        for (SatteliteModel sat : sattelites.values()) {
                            sat.moveBy(2);
                        }
                        for (BaliseModel bal : balises.values()) {
                            bal.move();
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
    public static void interpret(String line) {
        if (line.contains(":=")) {
            System.out.println("ass : "+line);
            handleAssignment(line);
        } else if (line.contains(".")) {
            System.out.println("call: "+line);
            handleMethodCall(line);
        }
    }

    public static void handleMethodCall(String line) {
        String[] parts = line.split("\\.");
        String varName = parts[0].trim();
        String methodCall = parts[1].trim();

        System.out.println("call0: "+methodCall);
        SatteliteModel objSat = sattelites.get(varName);
        BaliseModel objBal =  balises.get(varName);
        System.out.println("call1: "+objBal);
        System.out.println("call2: "+objSat);
        if (objSat != null && methodCall.equals("start();")) {

            SatteliteView satv = new SatteliteView(objSat);
            startedS.add(satv);
            topContainer.add(satv);
            System.out.println("call3: "+satv);
            System.out.println("call4: "+startedS);
        } else if (objBal != null && methodCall.equals("start();")) {
            BaliseView balv = new BaliseView(objBal);
            startedB.add(balv);
            bottomContainer.add(balv);
            System.out.println("call5: "+startedB);
        }
    }

    // Gérer une assignation d'objet (ex: sat1 := new Satellite(hauteur = 2000))
    public static void handleAssignment(String line) {
        String[] parts = line.split(":=");
        String varName = parts[0].trim();
        String objectCreation = parts[1].trim();
        System.out.println("ass1 : " +objectCreation );
        if (objectCreation.startsWith("new")) {
            String className = objectCreation.split(" ")[1];
            System.out.println("ass2 : " + className);
            if(className.equals("Satellite")){

                String params = objectCreation.substring(objectCreation.indexOf("(") + 1, objectCreation.indexOf(")"));
                SatteliteModel sat = new SatteliteModel(Integer.valueOf(params.split(",")[0]), Integer.valueOf(params.split(",")[1]));
                sat.configureToMoveToRight();
                sattelites.put(varName,sat);
                System.out.println("ass3 : "+sattelites);
            }
            if(className.equals("Balise")){
                String params = objectCreation.substring(objectCreation.indexOf("(") + 1, objectCreation.indexOf(")"));
               BaliseModel bal = new BaliseModel(Integer.valueOf(params.split(",")[0]), Integer.valueOf(params.split(",")[1]), 0, 400, 200);
               balises.put(varName,bal);
                System.out.println("ass3 : "+balises);
            }
        }
    }
}
