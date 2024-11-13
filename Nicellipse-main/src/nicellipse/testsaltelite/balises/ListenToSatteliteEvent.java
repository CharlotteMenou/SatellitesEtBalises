package nicellipse.testsaltelite.balises;

import nicellipse.testsaltelite.announcer.AbstractEvent;
import nicellipse.testsaltelite.sattelite.SatteliteView;

import java.util.List;

public class ListenToSatteliteEvent extends AbstractEvent {

    List<Object> listeners; // no usages yet

    public ListenToSatteliteEvent(Object source) {
        super(source);
    }

    public void sentTo(Object o) {
        ((SatteliteView) o).onSatteliteStop(this);
    }
}
