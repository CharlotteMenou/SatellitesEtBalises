package nicellipse.testsaltelite;

import nicellipse.component.NiRectangle;
import nicellipse.testsaltelite.announcer.AbstractEvent;
import nicellipse.testsaltelite.announcer.Announcer;

import java.awt.*;

public class MobiViewSatelite extends NiRectangle implements MobiListener {
	private static final long serialVersionUID = -8719870900105867735L;
	final Integer handCheckGap = 20;
	final Integer maxHandChecks = 1000;
	Integer handCheckCount;
	Color color;
	private MobiSatelite model;
	private Announcer announcer;

	public MobiViewSatelite(MobiSatelite model) {
		this.color = Color.red;
		this.handCheckCount = 0;
		this.announcer = new Announcer();
		this.model = model;
		this.model.register(this);
		this.setBackground(color);
		this.setSize(new Dimension(20, 20));
		this.setLocation(model.getX(), model.getY());
	}
	private void unregister(MobiViewSatelite mobiView, Class<? extends AbstractEvent> cls) {
		this.announcer.unregister(mobiView, cls);

	}

	@Override
	public void mobiMoveEvent(MobiMoveEvent evt) {
		MobiSatelite src = (MobiSatelite) evt.getSource();

		int x = src.getX();
		if (x > 0) {
			this.setLocation((x % this.getParent().getWidth()), src.getY());
		} else {
			this.setLocation(this.getParent().getWidth() - (Math.abs(x) % this.getParent().getWidth()), src.getY());
		}
		this.announcer.announce(new CollectDataRequestEvent(this));
	}

	@Override
	public void mobiCollectDataRequestEvent(CollectDataRequestEvent evt) {
		MobiViewSatelite mv = (MobiViewSatelite) evt.getSource();
		if ((this.getLocation().x >= (mv.getLocation().x - handCheckGap)
				&& (this.getLocation().x <= (mv.getLocation().x + this.getWidth() + handCheckGap)))) {
			mv.unregister(this, CollectDataRequestEvent.class);
		}
	}
}
