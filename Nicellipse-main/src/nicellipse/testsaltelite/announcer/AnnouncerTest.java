package nicellipse.testsaltelite.announcer;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

class MonEvent extends AbstractEvent {
	private static final long serialVersionUID = -8405476567648944095L;

	public MonEvent(Object source) {
		super(source);
	}

	public String toString() {
		return "MonEvent";
	}

	@Override
	public void sentTo(Object target) {
		((AnnouncerEventReceiver) target).receive(this);
	}
}

class MonEvent2 extends MonEvent {
	private static final long serialVersionUID = 4537860555195566435L;

	public MonEvent2(Object source) {
		super(source);
	}

	public String toString() {
		return "MonEvent2";
	}
}

interface AnnouncerEventReceiver {
	public void receive(AbstractEvent e);
}


class Balise implements AnnouncerEventReceiver {
	public static List<String> log = new ArrayList<String>();

	@Override
	public void receive(AbstractEvent e) {
		log.add(e.getSource().toString() + "->" + e.toString());
	}
}

public class AnnouncerTest {

	public String toString() {
		return "AnnouncerTest";
	}
	
	@Test
	public void test1() {
		Balise.log.clear();
		Announcer announcer = new Announcer();
		Balise b = new Balise();
		announcer.register(b, MonEvent.class);
		MonEvent evt = new MonEvent(this);
		announcer.announce(evt);
		assertTrue(Balise.log.size() == 1);
		assertTrue(Balise.log.get(0).equals("AnnouncerTest->MonEvent"));
	}
	@Test
	
	public void test2() {
		Balise.log.clear();
		Announcer announcer = new Announcer();
		Balise b = new Balise();
		announcer.register(b, MonEvent2.class);
		MonEvent2 evt = new MonEvent2(this);
		announcer.announce(evt);
		assertTrue(Balise.log.size() == 1);
		assertTrue(Balise.log.get(0).equals("AnnouncerTest->MonEvent2"));
	}

}
