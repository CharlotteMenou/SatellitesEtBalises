package nicellipse.testsaltelite.sattelite;

import nicellipse.testsaltelite.announcer.AbstractEvent;

public class SatteliteMoveEvent extends AbstractEvent {
    public SatteliteMoveEvent(Object source) {
        super(source);
    }

    public void sendTo(Object o) {
        ((SatteliteView) o).onSatteliteMove(this);
    }
}
