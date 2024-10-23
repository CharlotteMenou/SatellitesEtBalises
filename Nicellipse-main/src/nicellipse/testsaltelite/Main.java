import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;
import nicellipse.testsaltelite.MobiSatelite;
import nicellipse.testsaltelite.MobiViewSatelite;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public static void main (String[]args){
        NiSpace space = new NiSpace("Mobi 1", new Dimension(400, 400));
        NiRectangle container = new NiRectangle();
        container.setBackground(Color.lightGray);
        container.setSize(new Dimension(400, 400));
        space.add(container);

        MobiSatelite mobi1 = new MobiSatelite(0, 20);
        mobi1.configureToMoveToRight();
        MobiViewSatelite mobiV1 = new MobiViewSatelite(mobi1);

        MobiSatelite mobi2 = new MobiSatelite(0, 100);
        mobi2.configureToMoveToLeft();
        MobiViewSatelite mobiV2 = new MobiViewSatelite(mobi2);

        MobiSatelite mobi3 = new MobiSatelite(0, 150);
        mobi3.configureToMoveToLeft();
        MobiViewSatelite mobiV3 = new MobiViewSatelite(mobi3);

        MobiSatelite mobi4 = new MobiSatelite(0, 250);
        mobi4.configureToMoveToLeft();
        MobiViewSatelite mobiV4 = new MobiViewSatelite(mobi4);

        MobiSatelite mobi5 = new MobiSatelite(0, 300);
        mobi5.configureToMoveToRight();
        MobiViewSatelite mobiV5 = new MobiViewSatelite(mobi5);

        container.add(mobiV1);
        container.add(mobiV2);
        container.add(mobiV3);
        container.add(mobiV4);
        container.add(mobiV5);

        space.openInWindow();

        while (true) {
            final Runnable doit = new Runnable() {
                public void run() {
                    mobi1.moveBy(2);
                    mobi2.moveBy(5);
                    mobi3.moveBy(8);
                    mobi4.moveBy(10);
                    mobi5.moveBy(10);
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
            try {
                SwingUtilities.invokeAndWait(doit);
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
