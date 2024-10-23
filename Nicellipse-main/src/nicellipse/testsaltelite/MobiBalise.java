package nicellipse.testsaltelite;


import nicellipse.testsaltelite.announcer.Announcer;

public class MobiBalise {
	private Announcer announcer;
	private int x;
	private int y;
	private Integer dir;
	private String[] donnee;
	private Integer indice;
	private Boolean memoryFull ;

	public MobiBalise(int x, int y) {
		this.announcer = new Announcer();
		this.x = x;
		this.y = y;
		this.donnee =  new String[25];
		this.indice = 0;
		this.memoryFull = false;
	}

	void moveTo(int x, int y) {
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

	public void moveBy(int gap) {
		this.moveTo(this.x + (dir * gap), this.y);
	}

	public void sendData(){this.donnee = new String[25]; this.indice=0; this.memoryFull=false;}

	public void collectData(String data) {
		if (this.memoryFull) {
			this.donnee[indice] = data;
			indice = indice++;
		}
		if (indice > 24) {
			memoryFull = true;
		}
	}

	public boolean isFull(){return this.memoryFull;}

}
