package nicellipse.testsaltelite;


import nicellipse.testsaltelite.announcer.Announcer;

public class MobiSatelite {
	private Announcer announcer;
	private int x;
	private int y;
	private Integer dir;
	private int amplitude = 40; // Amplitude pour le mouvement sinusoïdal
	private double angle = 0; // Angle pour la sinusoïde
	private boolean movingRight = true;
	private boolean movingDown = true;

	public MobiSatelite(int x, int y) {
		this.announcer = new Announcer();
		this.x = x;
		this.y = y;
	}

	void moveTo(int x, int y) throws InterruptedException {
		this.x = x;
		this.y = y;
		this.announcer.announce(new MobiMoveEvent(this));
	}

	void register(Object o) {
		this.announcer.register(o, MobiMoveEvent.class);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void configureToMoveToLeft() {
		this.dir = -1;
	}

	public void configureToMoveToRight() {
		this.dir = 1;
	}

	public void moveBy(int gap) throws InterruptedException {
		this.moveTo(this.x + (dir * gap), this.y);
	}

}
