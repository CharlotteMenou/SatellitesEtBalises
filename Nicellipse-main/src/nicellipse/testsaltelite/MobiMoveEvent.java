package nicellipse.testsaltelite;

import nicellipse.testsaltelite.announcer.AbstractEvent;

public class MobiMoveEvent extends AbstractEvent {
	
	private static final long serialVersionUID = -2254460257653191974L;

	public MobiMoveEvent(Object source) {
		super(source);
	}

	public void sentTo(Object o) {
		((MobiListener)o).mobiMoveEvent(this);
	}

}
