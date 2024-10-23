package nicellipse.testsaltelite;

import java.awt.Color;
import java.awt.Dimension;
import nicellipse.component.NiRectangle;

import nicellipse.testsaltelite.announcer.AbstractEvent;
import nicellipse.testsaltelite.announcer.Announcer;

public class MobiViewBalise extends NiRectangle implements MobiListener {
	private static final long serialVersionUID = -8719870900105867735L;
	final Integer handCheckGap = 20;
	Integer handCheckCount;
	Color color;
	private MobiBalise model;
	private Announcer announcer;

	private boolean movingRight = true; // Direction horizontale
	private boolean movingDown = true;  // Direction verticale

	public MobiViewBalise(MobiBalise model) {
		this.color = Color.black;
		this.handCheckCount = 0;
		this.announcer = new Announcer();
		this.model = model;
		this.model.register(this);
		this.setBackground(color);
		this.setSize(new Dimension(20, 20));
		this.setLocation(model.getX(), model.getY());
	}
	private void unregister(MobiViewBalise mobiView, Class<? extends AbstractEvent> cls) {
		this.announcer.unregister(mobiView, cls);

	}

	@Override
	public void mobiMoveEvent(MobiMoveEvent evt) throws InterruptedException {
		// Récupérer les coordonnées du modèle
		MobiBalise src = (MobiBalise) evt.getSource();

		switch (model.getModeDeplacement()) {
			case 1:  // Déplacement horizontal (droite-gauche)
				int x = src.getX();
				if (x > 0) {
					// Si `x` est positif, on reste dans les limites de la largeur du parent
					this.setLocation((x % this.getParent().getWidth()), src.getY());
				} else {
					// Si `x` est négatif, on doit "rebondir" sur le bord gauche
					this.setLocation(this.getParent().getWidth() - (Math.abs(x) % this.getParent().getWidth()), src.getY());
				}
				break;

			case 0:  // Déplacement vertical (haut-bas)
				int y = src.getY();
				if (y > 0) {
					// Si `y` est positif, on reste dans les limites de la hauteur du parent
					this.setLocation(src.getX(), (y % this.getParent().getHeight()));
				} else {
					// Si `y` est négatif, on doit "rebondir" sur le bord supérieur
					this.setLocation(src.getX(), this.getParent().getHeight() - (Math.abs(y) % this.getParent().getHeight()));
				}
				break;
		}



        this.announcer.announce(new MobiMoveEvent(this));  // Annonce d'un événement de collecte de données
	}

	@Override
	public void mobiCollectDataRequestEvent(CollectDataRequestEvent evt) {
		MobiViewBalise mv = (MobiViewBalise) evt.getSource();
		if ((this.getLocation().x >= (mv.getLocation().x - handCheckGap)
				&& (this.getLocation().x <= (mv.getLocation().x + this.getWidth() + handCheckGap)))) {
			if (this.model.isFull()) {
				this.model.sendData();
				mv.unregister(this, CollectDataRequestEvent.class);
			}
		}
	}


}
