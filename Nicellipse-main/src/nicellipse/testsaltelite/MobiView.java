package nicellipse.testsaltelite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;
import nicellipse.testsaltelite.announcer.AbstractEvent;
import nicellipse.testsaltelite.announcer.Announcer;

public class MobiView extends NiRectangle implements MobiListener {
	private static final long serialVersionUID = -8719870900105867735L;
	final Integer handCheckGap = 20;
	final Integer maxHandChecks = 1000;
	Integer handCheckCount;
	Color color;
	private Mobi model;
	private Announcer announcer;

	public MobiView(Mobi model) {
		this.color = Color.red;
		this.handCheckCount = 0;
		this.announcer = new Announcer();
		this.model = model;
		this.model.register(this);
		this.setBackground(color);
		this.setSize(new Dimension(20, 20));
		this.setLocation(model.getX(), model.getY());
	}

	public void registerForHandCheck(MobiView v) {
		this.announcer.register(v, MobiHandCheckRequestEvent.class);
	}

	private void unregister(MobiView mobiView, Class<? extends AbstractEvent> cls) {
		this.announcer.unregister(mobiView, cls);

	}

	@Override
	public void mobiMoveEvent(MobiMoveEvent evt) {
		Mobi src = (Mobi) evt.getSource();

		int x = src.getX();
		if (x > 0) {
			this.setLocation((x % this.getParent().getWidth()), src.getY());
		} else {
			this.setLocation(this.getParent().getWidth() - (Math.abs(x) % this.getParent().getWidth()), src.getY());
		}
		this.announcer.announce(new MobiHandCheckRequestEvent(this));
	}

	@Override
	public void mobiHandCheckRequestEvent(MobiHandCheckRequestEvent evt) {
		MobiView mv = (MobiView) evt.getSource();
		if ((this.getLocation().x >= (mv.getLocation().x - handCheckGap)
				&& (this.getLocation().x <= (mv.getLocation().x + this.getWidth() + handCheckGap)))) {
			this.handCheckCount++;
			if (this.handCheckCount > this.maxHandChecks) {
				mv.unregister(this, MobiHandCheckRequestEvent.class);
				mv.setBackground(Color.black);
			} else {
				this.setBackground(Color.yellow);
				mv.setBackground(Color.yellow);
			}
		} else {
			this.setBackground(this.color);
			mv.setBackground(this.color);
		}
	}

	public static void main(String[] args) {
		NiSpace space = new NiSpace("Mobi 1", new Dimension(400, 400));
		NiRectangle container = new NiRectangle();
		container.setBackground(Color.lightGray);
		container.setSize(new Dimension(400, 400));
		space.add(container);

		Mobi mobi1 = new Mobi(0, 20);
		mobi1.configureToMoveToRight();
		MobiView mobiV1 = new MobiView(mobi1);

		Mobi mobi2 = new Mobi(0, 100);
		mobi2.configureToMoveToLeft();
		MobiView mobiV2 = new MobiView(mobi2);

		Mobi mobi3 = new Mobi(0, 150);
		mobi3.configureToMoveToLeft();
		MobiView mobiV3 = new MobiView(mobi3);

		Mobi mobi4 = new Mobi(0, 250);
		mobi4.configureToMoveToLeft();
		MobiView mobiV4 = new MobiView(mobi4);

		Mobi mobi5 = new Mobi(0, 300);
		mobi5.configureToMoveToRight();
		MobiView mobiV5 = new MobiView(mobi5);

		mobiV2.registerForHandCheck(mobiV1);
		mobiV3.registerForHandCheck(mobiV1);
		mobiV4.registerForHandCheck(mobiV1);
		mobiV5.registerForHandCheck(mobiV1);

		container.add(mobiV1);
		container.add(mobiV2);
		container.add(mobiV3);
		container.add(mobiV4);
		container.add(mobiV5);

		space.openInWindow();

		while (true) {
			final Runnable doit = new Runnable() {
				public void run() {
					mobi1.moveBy(2);
					mobi2.moveBy(5);
					mobi3.moveBy(8);
					mobi4.moveBy(10);
					mobi5.moveBy(10);
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			launch(doit);
		}
	}

	public static void launch(Runnable r) {
		try {
			SwingUtilities.invokeAndWait(r);
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

}
