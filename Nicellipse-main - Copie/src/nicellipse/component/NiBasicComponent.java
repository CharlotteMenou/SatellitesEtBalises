package nicellipse.component;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Shape;

public interface NiBasicComponent {

	void setBackground(Color background);

	void setForeground(Color foreground);

	void setLayout(LayoutManager m);

	void setBounds(int x, int y, int w, int h);

	int getWidth();

	int getHeight();

	Point getLocation();

	void setLocation(Point p);

	public Rectangle getBounds();

	default  Color defaultBackground() {
		return Color.lightGray;
	}

	default Color defaultForeground() {
		return Color.black;
	}

	default void defaultSetup() {
		this.setBackground(this.defaultBackground());
		this.setForeground(this.defaultForeground());
		this.setLayout(null);
	}

	default Shape getClipShape() {
		return this.getBounds();
	}

	default void setCenter(Point p) {
		Point c = new Point(p.x, p.y);
		c.translate(-(this.getWidth() / 2), -(this.getHeight() / 2));
		this.setBounds(c.x, c.y, this.getWidth(), this.getHeight());
	}
	
	default void setDimension(Dimension dim) {
		Point p = this.getLocation();
		this.setBounds(p.x, p.y, (int)dim.getWidth(), (int)dim.getHeight());
	}
	
	default Point getCenter() {
		Rectangle bnds = this.getBounds();
		Point c = new Point((int) bnds.getCenterX(), (int) bnds.getCenterY());
		Point l = this.getLocation();
		c.translate(-l.x, -l.y);
		return c;
	}
}
