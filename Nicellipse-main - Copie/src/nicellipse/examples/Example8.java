package nicellipse.examples;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.io.IOException;

import javax.swing.Timer;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiShape;
import nicellipse.component.NiSpace;

public class Example8 {
	public static void main(String[] args) throws IOException {
		NiSpace space = new NiSpace("Space with shapes", new Dimension(370, 320));

		NiRectangle container = new NiRectangle();
		container.setBackground(Color.white);
		container.setSize(new Dimension(1000,1000));
		container.setLocation(10, 10);
		
		space.add(container);

		int xPoly[] = {50,150,225,275,350,175,0};
        int yPoly[] = {50,0,25,125,150,275,200};
        Polygon polygon = new Polygon(xPoly, yPoly, xPoly.length);
		NiShape shape3 = new NiShape(polygon);
		shape3.setStroke(new BasicStroke(10));
		shape3.setForeground(Color.yellow);
		shape3.setBackground(Color.blue);
		Rectangle r = polygon.getBounds();
		shape3.setSize(r.width+r.x, r.height+r.y);
		container.add(shape3);
		
		Line2D line = new Line2D.Float(new Point(5,200), new Point(100,250));
		NiShape shape5 = new NiShape(line);
		shape5.setForeground(Color.black);
		shape5.setBackground(Color.red);
		shape5.setStroke(new BasicStroke(5));
		container.add(shape5);

		CubicCurve2D curve = new CubicCurve2D.Double(5, 5, 400, 40, 175, 250, 800, 5);
		NiShape shape6 = new NiShape(curve);
		container.add(shape6);
		
		space.openInWindow();
		
		int delay = 3000; // milliseconds
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				polygon.addPoint(10, 250);
				polygon.addPoint(236, 58);
				polygon.addPoint(144, 290);
				polygon.addPoint(0, 0);
				shape3.refresh();
			}
		};
		Timer animation = new Timer(delay, taskPerformer);
		animation.setRepeats(false);
		animation.start();
		
	}

}
