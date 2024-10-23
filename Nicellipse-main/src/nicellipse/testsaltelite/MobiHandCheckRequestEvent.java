package nicellipse.testsaltelite;


import nicellipse.testsaltelite.announcer.AbstractEvent;

public class MobiHandCheckRequestEvent extends AbstractEvent {
	
	public MobiHandCheckRequestEvent(Object source) {
		super(source);
	}
	
	private static final long serialVersionUID = 2196828859112483898L;

	@Override
	public void sentTo(Object o) {
		((MobiListener)o).mobiHandCheckRequestEvent(this);
	}

}
