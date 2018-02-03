package de.tubs.carsten.sq2.pooling.distributed;

import java.util.Observable;

public class PoolStatus extends Observable{

	private boolean running;

	public PoolStatus(boolean running) {
		super();
		this.running = running;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void setAndNotify(boolean running){
		setRunning(running);

		setChanged();
		notifyObservers(running);
	}
	
	
}
