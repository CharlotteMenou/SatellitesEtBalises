package nicellipse.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;

public class Exercice1V1 {
	NiSpace space = new NiSpace("Exercice 1", new Dimension(200, 150));
	NiRectangle robi = new NiRectangle();

	void moveRobi(int x, int y) {
		final Runnable doit = new Runnable() {
			public void run() {
				robi.setLocation(new Point(x, y));
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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

	public Exercice1V1() {
		space.setBackground(Color.lightGray);
		robi.setBackground(Color.red);
		robi.setSize(20, 20);
		space.add(robi);

		space.openInWindow();

		int x = 0, y = 0;
		while (true) {
			while (x < space.getWidth() - robi.getWidth()) {
				x = x + 1;
				this.moveRobi(x, y);
			}
			while (y < space.getHeight() - robi.getHeight()) {
				y = y + 1;
				this.moveRobi(x, y);
			}
			while (x > 0) {
				x = x - 1;
				this.moveRobi(x, y);
			}
			while (y > 0) {
				y = y - 1;
				this.moveRobi(x, y);
			}
			robi.setBackground(new Color((int) (Math.random() * 0x1000000)));
		}
	}

	public static void main(String[] args) {
		new Exercice1V1();
	}

}
