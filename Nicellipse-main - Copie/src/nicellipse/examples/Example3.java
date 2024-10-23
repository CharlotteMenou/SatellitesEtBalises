package nicellipse.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

import nicellipse.component.NiImage;
import nicellipse.component.NiSpace;

public class Example3 {
	public static void main(String[] args) {
		
		File path = new File("I_love_Brest_city.jpg");
		BufferedImage rawImage = null;
		try {
			rawImage = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Dimension dim = new Dimension(rawImage.getWidth()+20, rawImage.getHeight()+20);
		NiSpace space = new NiSpace("Space with an image", dim);

		NiImage image = new NiImage(rawImage);
		image.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
		image.setLocation(10,10);
		space.add(image);
		
		space.openInWindow();
	}

}
