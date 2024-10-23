package nicellipse.testsaltelite;


import nicellipse.testsaltelite.announcer.Announcer;

public class MobiBalise {
	private Announcer announcer;
	private int x;
	private int y;
	private String[] donnee;
	private Integer indice;
	private Boolean memoryFull ;
	private int modeDeplacement;

	public int getModeDeplacement() {
		return modeDeplacement;
	}


	public MobiBalise(int x, int y, int modeDeplacement) {
        this.modeDeplacement = modeDeplacement;
        this.announcer = new Announcer();
		this.x = x;
		this.y = y;
		this.donnee =  new String[25];
		this.indice = 0;
		this.memoryFull = false;
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


	public void moveBy(int gap) throws InterruptedException {
		this.moveTo(this.x + (gap), this.y);
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
