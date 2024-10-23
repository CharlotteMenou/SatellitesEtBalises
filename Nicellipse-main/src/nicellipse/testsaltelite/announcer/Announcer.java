package nicellipse.testsaltelite.announcer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Announcer {
	Map<Class<? extends AbstractEvent>, List<Object>> registrationIndex;
	
	public Announcer() {
		registrationIndex = new HashMap<>();
	}
	
	public void register(Object o, Class<? extends AbstractEvent> eventClass) {
		List<Object> l = registrationIndex.get(eventClass);
		if (l == null) {
			l = new ArrayList<Object>();
			registrationIndex.put(eventClass, l );
		}
		l.add(o);
	}

	public void unregister (Object o, Class<? extends AbstractEvent> eventClass) {
		List<Object> l = registrationIndex.get(eventClass);
		Iterator<Object> itor =  l.iterator();
		while (itor.hasNext()) {
			Object current = itor.next();
			if (o == current) itor.remove();
		}
		if (l.isEmpty()) {
			registrationIndex.remove(eventClass);
		}
	}
	
	public void announce(AbstractEvent anEvent) {
		Class<?> eventClass = anEvent.getClass();
		List<Object> l = registrationIndex.get(eventClass);
		if (l == null) return;
		Object [] registered = l.toArray();
		for (Object current : registered) {
			anEvent.sentTo(current);
		}
	}
}
