package nicellipse.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

public class NiEllipse extends JComponent implements NiBorderedComponent {
	private static final long serialVersionUID = -8346296675140338192L;
	Color borderColor;
	Stroke stroke;
	boolean withBorder;
	private Ellipse2D ellipse;

	public NiEllipse() {
		this.defaultSetup();
		this.ellipse = new Ellipse2D.Double(0, 0, 5, 5);
		this.setBounds(this.ellipse.getBounds());
		this.withBorder = true;
		this.stroke = this.defaultStroke();
		this.borderColor = this.defaultBorderColor();
	}

	public Border defaultBorder() {
		return BorderFactory.createEmptyBorder();
	}

	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		this.ellipse = new Ellipse2D.Double(0, 0, w, h);
	}
	
	public Shape getClipShape() {
		return this.ellipse;
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.clip(this.getClipShape());
		super.paint(g2d);
		g2d.dispose();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(this.getBackground());
		super.paintComponent(g2d);
		g2d.fill(ellipse);
		if (this.withBorder) {
			g2d.setColor(this.borderColor);
			g2d.setStroke(this.stroke);
			g2d.draw(this.ellipse);
		}
		g2d.dispose();
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public void setStrokeWidth(float w) {
		this.setStroke(new BasicStroke(w, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
	}

	public void setWithBorder(Boolean withBorder) {
		this.withBorder = withBorder;
	}

	public Boolean containsPoint(Point p) {
		return this.ellipse.contains(p);
	}

}
