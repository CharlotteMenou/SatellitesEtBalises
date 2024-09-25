package nicellipse.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nicellipse.component.NiLabel;
import nicellipse.component.NiSpace;

public class Example0 {
	public static void main(String[] args) {
		JPanel space = new NiSpace("Empty Space", new Dimension(300, 200));
		JLabel label = new NiLabel("space " + space.getWidth() + "x" + space.getHeight() );
		label.setFont(new Font("Arial", Font.BOLD, 24));
		label.setForeground(Color.darkGray);
		label.setBackground(Color.yellow);
		space.add(label);

		JFrame frame = new JFrame("Empty Space");
		WindowAdapter wa = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.getContentPane().add(space);
		frame.pack();
		frame.setVisible(true);

	
	}
}
