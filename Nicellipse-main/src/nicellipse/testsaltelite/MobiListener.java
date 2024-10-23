package nicellipse.testsaltelite;

public interface MobiListener {
	
	public void mobiMoveEvent(MobiMoveEvent evt) throws InterruptedException;
	public void mobiCollectDataRequestEvent(CollectDataRequestEvent evt) throws InterruptedException;
}
