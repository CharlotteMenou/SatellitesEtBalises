package nicellipse.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nicellipse.component.NiEllipse;
import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;

public class Exercice1V3 {
	final int FPS_MIN = 10;
	final int FPS_MAX = 1000;
	final int FPS_INIT = 250;
	final int startDelay = 1000 / FPS_INIT;
	Timer animation;
	NiEllipse robi = new NiEllipse();
	NiRectangle robiHome = new NiRectangle();
	JLabel roundCounterLabel = new JLabel("0");
	Integer roundCounter = 0;

	private void setupAnimation() {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Point loc = robi.getLocation();
				if (loc.y == 0 && loc.x < robiHome.getWidth() - robi.getWidth()) {
					loc.translate(1, 0);
				} else if (loc.x > 0 && loc.y < robiHome.getHeight() - robi.getHeight()) {
					loc.translate(0, 1);
				} else if (loc.x > 0) {
					loc.translate(-1, 0);
				} else if (loc.y > 0) {
					loc.translate(0, -1);
				}
				if (loc.x == 0 && loc.y == 0) {
					robi.setBackground(new Color((int) (Math.random() * 0x1000000)));
				}
				robi.setLocation(loc);
				if (loc.x == 0 && loc.y == 0) {
					roundCounter++;
					roundCounterLabel.setText(roundCounter.toString());
				}	
			}
		};
		this.animation = new Timer(startDelay, taskPerformer);
		this.animation.setRepeats(true);
	}
	
	private JPanel fpsSliderPanel() {		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel label = new JLabel("FPS:", JLabel.RIGHT);
		JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);

		framesPerSecond.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int fps = (int) source.getValue();
					int newDelay = 1000 / fps;
					animation.setDelay(newDelay);
					animation.setInitialDelay(newDelay * 10);
				}
			}
		});

		// Turn on labels at major tick marks.
		framesPerSecond.setMajorTickSpacing(500);
		framesPerSecond.setMinorTickSpacing(100);
		framesPerSecond.setPaintTicks(true);
		framesPerSecond.setPaintLabels(false);
		
		panel.add(label);
		panel.add(framesPerSecond);
		return panel;
	}
	
	private JPanel buttonPanel() {
		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animation.start();
				start.setEnabled(false);
				stop.setEnabled(true);
			}
		});
		stop.setEnabled(false);
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animation.stop();
				start.setEnabled(true);
				stop.setEnabled(false);
			}
		});
		// Lay out the buttons from left to right.
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel roundLabel = new JLabel("Round: ", JLabel.LEFT);

		panel.add(start);
		panel.add(roundLabel);
		panel.add(roundCounterLabel);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(Box.createHorizontalGlue());
		panel.add(this.fpsSliderPanel());
		panel.add(Box.createHorizontalGlue());
		panel.add(stop);
		return panel;
	}

	public Exercice1V3() {
		NiSpace space = new NiSpace("The robi run", new Dimension(500, 500));
		this.setupAnimation();		
		this.robi.setSize(30, 30);
		this.robiHome.setBackground(Color.white);
		this.robiHome.add(robi);

		// Lay out from top to bottom. everything.
		// Do not have to set the size of space
		// because its size is automatically set by its layout
		space.setLayout(new BoxLayout(space, BoxLayout.Y_AXIS));
		space.add(this.robiHome);
		space.add(this.buttonPanel());
		space.openInWindow();
	}

	public static void main(String[] args) {
		new Exercice1V3();
	}

}
