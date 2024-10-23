package nicellipse.examples;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import nicellipse.component.NiEllipse;
import nicellipse.component.NiLabel;
import nicellipse.component.NiPolyLine;
import nicellipse.component.NiRectangle;
import nicellipse.component.NiShape;
import nicellipse.component.NiSpace;

public class Example2 {
	public static void main(String[] args) {
		NiSpace space = new NiSpace("With flow layout", new Dimension(1000, 1000));
		space.setLayout(new FlowLayout());
		((FlowLayout) space.getLayout()).setAlignment(FlowLayout.TRAILING);
		
		NiLabel label = new NiLabel("Head");
		label.setFont(new Font("Arial", Font.BOLD, 24));
		label.setForeground(Color.darkGray);
		label.setBackground(Color.yellow);
		space.add(label);
		
		NiEllipse ellipse = new NiEllipse();
		ellipse.setBackground(Color.magenta);
		ellipse.setSize(new Dimension(50,50));
		ellipse.setPreferredSize(new Dimension(50,50));
		space.add(ellipse);
		
		NiEllipse subellipse = new NiEllipse();
		subellipse.setBackground(Color.white);
		subellipse.setSize(10,10);
		subellipse.setCenter(ellipse.getCenter());
		ellipse.add(subellipse);
		
		NiRectangle rectangle = new NiRectangle();
		rectangle.setBackground(Color.yellow);	
		rectangle.setPreferredSize(new Dimension(30,30));
		space.add(rectangle);
		
		NiEllipse ellipse2 = new NiEllipse();
		ellipse2.setBackground(Color.black);	
		ellipse2.setPreferredSize(new Dimension(30,30));
		space.add(ellipse2);
		
		NiRectangle rectangle2 = new NiRectangle();
		rectangle2.setBackground(Color.yellow);	
		rectangle2.setPreferredSize(new Dimension(30,30));
		space.add(rectangle2);

		NiEllipse ellipse3 = new NiEllipse();
		ellipse3.setBackground(Color.black);	
		ellipse3.setPreferredSize(new Dimension(30,30));
		space.add(ellipse3);
		
		NiRectangle rectangle3 = new NiRectangle();
		rectangle3.setBackground(Color.yellow);	
		rectangle3.setPreferredSize(new Dimension(30,30));
		space.add(rectangle3);
		
		NiLabel label2 = new NiLabel("Tail");
		label2.setFont(new Font("Arial", Font.BOLD, 24));
		label2.setForeground(Color.darkGray);
		label2.setBackground(Color.yellow);
		space.add(label2);
		
		NiShape shape1 = new NiShape();
		space.add(shape1);
		
		NiShape shape2 = new NiShape(new Ellipse2D.Double(0, 0, 60, 80));
		shape2.setBackground(Color.gray);
		space.add(shape2);
		
		int xPoly[] = {50,150,225,275,350,175,0};
        int yPoly[] = {50,0,25,125,150,275,200};
        Polygon polygon = new Polygon(xPoly, yPoly, xPoly.length);
		NiShape shape3 = new NiShape(polygon);
		shape3.setStroke(new BasicStroke(10));
		shape3.setForeground(Color.yellow);
		shape3.setBackground(Color.blue);
		space.add(shape3);

		Arc2D arc = new Arc2D.Float(0, 0, 200, 200, 90, 90, Arc2D.PIE);
		NiShape shape4 = new NiShape(arc);
		shape4.setForeground(Color.black);
		shape4.setBackground(Color.red);
		shape4.setStroke(new BasicStroke(5));
		space.add(shape4);
		
		Line2D line = new Line2D.Double(new Point(0,0), new Point(100,100));
		NiShape shape5 = new NiShape(line);
		shape5.setForeground(Color.black);
		shape5.setBackground(Color.red);
		shape5.setStroke(new BasicStroke(5));
		space.add(shape5);
		
		CubicCurve2D curve = new CubicCurve2D.Double(5, 5, 400, 40, 175, 250, 500, 5);
		NiShape shape6 = new NiShape(curve);
		shape5.setStroke(new BasicStroke(4));
		space.add(shape6);
		
		NiPolyLine pline = new NiPolyLine();
		for (int i = 0; i < xPoly.length; i++)
			pline.addPoint(new Point(xPoly[i], yPoly[i]));
		pline.setStroke(new BasicStroke(4));
		pline.setSize(400,400);
		pline.setPreferredSize(pline.getSize());
		space.add(pline);
		
		
		space.openInWindow();
		space.repaint();
		
		
	
	}
}
