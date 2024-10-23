package nicellipse.testsaltelite.balises;
import nicellipse.testsaltelite.announcer.AbstractEvent;


public class MoveBaliseEvent extends AbstractEvent  {

    public MoveBaliseEvent(Object source) {
        super(source);
    }

    public void sentTo(Object o) {
        ((BaliseView) o ).onBaliseMove(this);
    }
}
