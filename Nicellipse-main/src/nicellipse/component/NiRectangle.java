package nicellipse.component;

import java.awt.Dimension;

import javax.swing.JPanel;

public class NiRectangle extends JPanel implements NiBorderedComponent {
	private static final long serialVersionUID = 128422045550852289L;

	public NiRectangle() {
		this.defaultSetup();
		this.setDimension(new Dimension(20,20));
		this.setLayout(null);
	}
	
}
