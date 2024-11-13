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
import java.util.Scanner;

public class TestTerminal {
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

        // Création et configuration du satellite
        SatteliteModel satellite1 = new SatteliteModel(0, 20);
        satellite1.configureToMoveToRight();
        SatteliteView satelliteV2 = new SatteliteView(satellite1);

        // Demander à l'utilisateur d'entrer des balises
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le nombre de balises à ajouter : ");
        int numBalises = scanner.nextInt();
        scanner.nextLine();  // Consomme la nouvelle ligne après le nombre

        // Créer et ajouter les balises
        BaliseModel[] balises = new BaliseModel[numBalises];
        BaliseView[] baliseViews = new BaliseView[numBalises];

        for (int i = 0; i < numBalises; i++) {
            System.out.println("Entrez les coordonnées de la balise " + (i + 1) + " (x y) : ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            scanner.nextLine();  // Consomme la nouvelle ligne

            // Créer un modèle de balise avec les coordonnées spécifiées
            balises[i] = new BaliseModel(x, y, 0, 400, 200);

            // Créer une vue de la balise
            baliseViews[i] = new BaliseView(balises[i]);

            // Ajouter la vue de la balise au conteneur
            bottomContainer.add(baliseViews[i]);
        }

        // Ajouter le satellite au conteneur supérieur
        topContainer.add(satelliteV2);

        // Ouvrir l'espace dans une fenêtre
        space.openInWindow();

        // Exécution des mouvements
        while (true) {
            final Runnable doit = new Runnable() {
                public void run() {
                    // Déplacer le satellite
                    satellite1.moveBy(2);

                    // Déplacer chaque balise
                    for (int i = 0; i < numBalises; i++) {
                        balises[i].move();  // Faire bouger chaque balise
                        // Mettre à jour la position de la vue de la balise
                        baliseViews[i].repaint();
                    }

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
