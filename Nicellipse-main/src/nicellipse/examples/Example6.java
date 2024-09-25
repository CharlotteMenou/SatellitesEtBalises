package nicellipse.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Timer;

import nicellipse.component.NiLabel;
import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;

public class Example6 {

	public static void main(String[] args) {
		NiLabel label;
		NiSpace space = new NiSpace("Some rectangles with labels", new Dimension(1000, 400));
		
		NiRectangle container = new NiRectangle();
		container.setBackground(Color.white);
		container.setSize(new Dimension(900,300));
		container.setLocation(50, 50);
		label = new NiLabel("container " + container.getWidth() + "x" + container.getHeight() );
		label.setForeground(Color.red);
		label.setFont(new Font("Arial", Font.BOLD, 20));
		container.add(label);
		
		NiRectangle subContainer = new NiRectangle();
		subContainer.setBackground(Color.black);
		subContainer.setLocation(new Point(30,50));
		subContainer.setSize(new Dimension(700,200));
		subContainer.setBorder(BorderFactory.createLineBorder(Color.red, 4));
		label = new NiLabel("subContainer " + subContainer.getWidth() + "x" + subContainer.getHeight() );
		label.setForeground(Color.white);
		label.setFont(new Font("Arial", Font.BOLD, 18));
		subContainer.add(label);
		container.add(subContainer);
		
		NiRectangle subsubContainer = new NiRectangle();
		subsubContainer.setBackground(Color.white);
		subsubContainer.setLocation(new Point(50,50));
		subsubContainer.setSize(new Dimension(600,120));
		NiLabel animLabel = new NiLabel("subsubContainer " + subsubContainer.getWidth() + "x" + subsubContainer.getHeight() );
		animLabel.setForeground(Color.blue);
		animLabel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
		subsubContainer.add(animLabel);
		subsubContainer.setBorder(BorderFactory.createEmptyBorder());
		subContainer.add(subsubContainer);

		space.add(container);
		
		space.openInWindow();
		
		// space width and height are available only after it is opened
		label = new NiLabel("space " + space.getWidth() + "x" + space.getHeight() );
		label.setFont(new Font("Arial", Font.BOLD, 24));
		label.setForeground(Color.darkGray);
		label.setBackground(Color.yellow);
		space.add(label);
		space.repaint();
		
		int delay = 3000; // milliseconds
		ActionListener taskPerformer = new ActionListener() {
			Random x = new Random();
			String t = null;
			public void actionPerformed(ActionEvent evt) {
				if (t == null) t=animLabel.getText();
				animLabel.setLocation(new Point(x.nextInt(400), x.nextInt(110)));
				animLabel.setText(t + " at " + animLabel.getX() + "X" + animLabel.getY());
			}
		};
		Timer animation = new Timer(delay, taskPerformer);
		animation.start();

		
		
		
	}

}
