package nicellipse.testsaltelite;

import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;
import nicellipse.testsaltelite.announcer.AbstractEvent;
import nicellipse.testsaltelite.announcer.Announcer;
import nicellipse.testsaltelite.sattelite.SatteliteView;

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

    public void registerForHandCheck(SatteliteView v) {
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
}
