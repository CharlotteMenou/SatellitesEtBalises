package nicellipse.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class NiImage extends JComponent implements NiBasicComponent {
	private static final long serialVersionUID = 606824579470358701L;
	private Image image;

	public NiImage(Image image) {
		this.setImage(image);
	}
	
	public NiImage(File path) throws IOException {
		BufferedImage rawImage = null;
		rawImage = ImageIO.read(path);
		this.setImage(rawImage);
	}
	
	public void setImage(Image image) {
		this.image = image;
		int width = this.image.getWidth(null);
		int height = this.image.getHeight(null);
		this.setSize(width, height);
	}
		
	public Shape getClipShape() {
		return new Rectangle(0,0,this.getWidth(), this.getHeight());
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		super.paintComponent(g2d);
		g2d.clip(this.getClipShape());
		g2d.drawImage(this.image, 0, 0, null);
		g2d.dispose();
	}
}
