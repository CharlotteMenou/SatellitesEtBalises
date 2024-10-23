package nicellipse.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Component;
import nicellipse.component.NiEllipse;
import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;


// Model 
class Something {
	Point pos;
	
	public Something(Point position) {
		this.pos = position;
	}
	
	public Point getPosition() {
		return pos;
	}
	
	public void move () {
		Random rand = new Random();
		this.pos.x = this.pos.x + rand.nextInt(11) - 5;
		this.pos.y = this.pos.y + rand.nextInt(11) - 5;
	}
}

// View : it knows its model
class SomethingView extends NiEllipse {
	private static final long serialVersionUID = 8010266472160477056L;
	Something thing;
	
	public SomethingView(Something model) {
		this.thing = model;
		this.setBackground(new Color((int) (Math.random() * 0x1000000)));
		this.setDimension(new Dimension(4,4));
		this.setLocation(this.thing.getPosition());
	}
	
	public void thingHasChanged() {
		this.setLocation(this.thing.getPosition());
	}
}


// The animation spec
class GraphicAnimation implements ActionListener {
	final int graphicAnimationDelay = 10;
	NiRectangle home;
	Timer animation;
	
	public GraphicAnimation(NiRectangle home, int startDelay) {
		this.home = home;
		this.animation = new Timer(0, this);
		this.setDelay(startDelay);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Component[] views =  this.home.getComponents();
		for (int i = 0; i < views.length; i++) {
			SomethingView next = (SomethingView) views[i];
			next.thing.move();
			next.thingHasChanged();
		}
	}
	
	public void start() {
		
		this.animation.start();
	}

	public void setDelay(int newDelay) {
		this.animation.setDelay(newDelay);
	}

	public void setInitialDelay(int i) {
		this.animation.setInitialDelay(i);
		
	}
	
}

// The main app
public class MovingThings {
	ArrayList<Something> population;
	NiRectangle home;
	GraphicAnimation grAnimation;
	final int FPS_MIN = 2;
	final int FPS_MAX = 500;
	final int FPS_INIT = 10;
	final int startDelay = FPS_MAX / FPS_INIT;
	
	public MovingThings() {
		population = new ArrayList<>();
		NiSpace space = new NiSpace("Moving things", new Dimension(1000, 600));
		this.home = new NiRectangle();
		this.home.setBounds(100, 100, 600, 400);
		this.home.setBackground(Color.lightGray);
		JPanel fpsSlider = this.fpsSliderPanel();
		fpsSlider.setLocation(0,0);
		fpsSlider.setSize(new Dimension(500,30));
		JPanel popSlider = this.populationSliderPanel();
		popSlider.setLocation(0,40);
		popSlider.setSize(new Dimension(950, 40));
		
		this.grAnimation = new GraphicAnimation(this.home, this.startDelay);

		space.add(this.home);
		space.add(fpsSlider);
		space.add(popSlider);
		space.setDoubleBuffered(true);
		
		this.initPopulation(200);

		space.openInWindow();
	}
	
	private JPanel fpsSliderPanel() {		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel label = new JLabel(" FPS :", JLabel.RIGHT);
		JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);

		framesPerSecond.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int fps = (int) source.getValue();
					int newDelay = FPS_MAX / fps;
					grAnimation.setDelay(newDelay);
				}
			}
		});

		// Turn on labels at major tick marks.
		framesPerSecond.setMajorTickSpacing(50);
		framesPerSecond.setMinorTickSpacing(10);
		framesPerSecond.setPaintTicks(true);
		framesPerSecond.setPaintLabels(false);
		
		panel.add(label);
		panel.add(framesPerSecond);
		return panel;
	}
	
	private JPanel populationSliderPanel() {		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel label = new JLabel(" Population :", JLabel.RIGHT);
		JSlider pop = new JSlider(JSlider.HORIZONTAL, 0, 10000, 200);

		pop.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int nb = (int) source.getValue();
					initPopulation(nb);
				}
			}
		});

		// Turn on labels at major tick marks.
		pop.setMajorTickSpacing(500);
		pop.setMinorTickSpacing(500);
		pop.setPaintTicks(true);
		pop.setPaintLabels(true);
		
		panel.add(label);
		panel.add(pop);
		return panel;
	}

	private void initPopulation(int nb) {

		Component[] views =  this.home.getComponents();
		for (int i = 0; i < views.length; i++) {
			SomethingView next = (SomethingView) views[i];
			this.home.remove(next);
		}
		population.clear();
		
		int width = this.home.getWidth();
		int height = this.home.getHeight();
		Random x = new Random();
		for (int i = 0; i < nb; i++) {
			Something thing = new Something(new Point(x.nextInt(width), x.nextInt(height)));
			this.population.add(thing);
			SomethingView view = new SomethingView(thing);
			this.home.add(view);
		}
	}
	
	
	public void startGraphicAnimation() {
		grAnimation.start();
	}
		
	public static void main(String args[]) {
		MovingThings movingThings = new MovingThings();
		movingThings.startGraphicAnimation();
	}


}
