package nicellipse.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;

public class Exercice1V2 {
	NiSpace space = new NiSpace("Exercice 1", new Dimension(200, 150));
	NiRectangle robi = new NiRectangle();
	
	public Exercice1V2() {
		space.setBackground(Color.lightGray);
		robi.setBackground(Color.red);
		robi.setSize(20, 20);
		space.add(robi);

		space.openInWindow();
		
		int delay = 1; // milliseconds
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Point loc = robi.getLocation();
				if (loc.y == 0 && loc.x < space.getWidth() - robi.getWidth()) {
					loc.translate(1,0);
				} else if (loc.x > 0 && loc.y < space.getHeight() - robi.getHeight()) {
					loc.translate(0,1);
				} else if (loc.x > 0) {
					loc.translate(-1,0);
				} else if (loc.y > 0) {
					loc.translate(0,-1);
				}
				if (loc.x == 0 && loc.y == 0) {
					robi.setBackground(new Color((int)(Math.random() * 0x1000000)));
				}
				robi.setLocation(loc);
			}
		};
		Timer animation = new Timer(delay, taskPerformer);
		animation.setRepeats(true);
		animation.start();
	}
	
	public static void main(String[] args) {
		new Exercice1V2();
	}
	
}

