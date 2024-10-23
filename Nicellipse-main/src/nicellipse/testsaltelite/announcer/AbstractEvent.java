package nicellipse.testsaltelite.announcer;
import java.util.EventObject;

public abstract class AbstractEvent extends EventObject {
	
	private static final long serialVersionUID = -3665126000236217922L;

	public AbstractEvent(Object source) {
		super(source);
	}
	public void sentTo(Object target) {}
}
