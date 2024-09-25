package nicellipse.examples;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;

import nicellipse.component.NiEllipse;
import nicellipse.component.NiPolyLine;
import nicellipse.component.NiRectangle;
import nicellipse.component.NiShape;
import nicellipse.component.NiSpace;

public class Example4 {
	public static void main(String[] args) {
		
		NiSpace space = new NiSpace("Some components", new Dimension(800,600));

		CubicCurve2D curve = new CubicCurve2D.Double(5, 5, 400, 40, 175, 250, 500, 5);
		NiShape shape6 = new NiShape(curve);
		shape6.setStroke(new BasicStroke(4));
		shape6.doFilling(false);
		space.add(shape6);

		Line2D line = new Line2D.Double(10,10,2000,500);
		NiShape shape7 = new NiShape(line);
		shape7.setStrokeWidth(5);
		space.add(shape7);
		
		
		NiEllipse ellipse = new NiEllipse();
		ellipse.setBounds(10,10,270,270);
		ellipse.setBackground(Color.yellow);
		space.add(ellipse);
		
		NiPolyLine pline = new NiPolyLine();
		pline.setStrokeWidth(8);
		pline.setBackground(Color.lightGray);
		pline.setLocation(0,0);
		pline.setSize(800,600);
		pline.addPoint(new Point(205,340));
		pline.addPoint(new Point(250,380));
		pline.addPoint(new Point(270,330));
		pline.addPoint(new Point(295,380));
		pline.addPoint(new Point(350,330));
		pline.addPoint(new Point(370,380));
		space.add(pline);
		
		NiRectangle pane = new NiRectangle();
		pane.setBounds(140, 140, 200, 200);
		pane.setBackground(Color.red);
		ellipse.add(pane);

		NiRectangle innerPane = new NiRectangle();
		innerPane.setBounds(30, 30, 300, 300);
		innerPane.setBackground(Color.gray);
		pane.add(innerPane);

		
		space.openInWindow();
	}

}
