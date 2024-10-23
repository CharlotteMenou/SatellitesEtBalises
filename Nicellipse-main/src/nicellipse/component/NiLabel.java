package nicellipse.component;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.UIManager;

public class NiLabel extends JLabel {
	private static final long serialVersionUID = 8320838214536741040L;
	private FontMetrics metrics;
	private Font font;
	
	public NiLabel() {
		this.reset();
	}
	
	public NiLabel(String l) {
		super(l);
	}
	
	public Font defaultFont() {
		return (Font) UIManager.getDefaults().get("TextField.font");
	}
	
	public void setFontSize(int size) {
		this.setFont(new Font(this.font.getFontName(), this.font.getStyle(), size));
	}
	
	private void reset() {
		this.metrics = null;
		this.font = this.defaultFont();
		this.setSize(1, 1);
		this.repaint();
	}
	
	private void ensureMetric(Graphics g) {
		font = g.getFont();
		metrics = g.getFontMetrics(font);
	}
	
	public void setText(String s) {
		this.reset();
		super.setText(s);
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		if (this.metrics == null) {
			this.ensureMetric(g2d);
		}
		Dimension dim = new Dimension(metrics.stringWidth(this.getText()), metrics.getHeight());
		if (!this.getSize().equals(dim)) {
			this.setSize(dim.width+6, dim.height);
		}
		super.paintComponent(g2d);
		g2d.dispose();
	}
}
