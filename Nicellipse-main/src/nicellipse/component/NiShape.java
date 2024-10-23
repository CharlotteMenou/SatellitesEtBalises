package nicellipse.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;

import javax.swing.JComponent;

public class NiShape extends JComponent implements NiBasicComponent {
	private static final long serialVersionUID = -3829818147581275076L;
	Shape shape;
	Stroke stroke;
	Boolean fill = true;
	Boolean outline = true;
	
	public void doFilling(Boolean f) {
		if (f == this.fill) return;
		this.fill = f;
		this.repaint();
	}
	public void doOutline(Boolean o) {
		if (o == this.outline) return;
		this.outline = o;
		this.repaint();
	}
	
	public Shape defaultShape() {
		return new Rectangle(0,0,40,40);
	}
	
	public Stroke defaultStroke() {
		return new BasicStroke(1);
	}
	
	public Color defaultBackground() {
		return Color.white;
	}

	public Color defaultForeground() {
		return Color.black;
	}
	
	public NiShape() {
		this.shape = this.defaultShape();
		this.defaultSetup();
	}
	
	public NiShape(Shape shape) {
		this();
		this.setShape(shape);
	}

	public void setShape(Shape shape) {
		this.shape = shape;
		this.setUp();
	}
	
	public void defaultSetup() {
		this.setBackground(this.defaultBackground());
		this.setForeground(this.defaultForeground());
		this.setLayout(null);
		stroke = this.defaultStroke();
		this.updateSize();
	}
	
	protected void updateSize() {
		Rectangle r = this.shape.getBounds();
		this.setSize(r.width+r.x, r.height+r.y);
		this.setPreferredSize(this.getSize());
	}
	
	protected void setUp() {
		this.updateSize();
	}
	
	public void refresh() {
		this.setUp();
	}
		
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!(this.fill || this.outline)) return;
		Graphics2D g2d = (Graphics2D) g.create();
		if (this.fill) {
			g2d.setColor(this.getBackground());
			g2d.fill(this.shape);
		}
		if (this.outline) {
			g2d.setColor(this.getForeground());
			g2d.setStroke(this.stroke);
			g2d.draw(this.shape);
		}
		g2d.dispose();
	}
	
	public Shape getClipShape() {
		return this.shape;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public void setStrokeWidth(float w) {
		this.setStroke(new BasicStroke(w, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
	}

	public void setLineColor(Color color) {
		this.setForeground(color);
	}

}
