package de.tubs.carsten.sq2.pooling.distributed;

import java.util.Observable;

public class NewResultNotifier extends Observable {

	public void notifyAboutResult(Object o){
		setChanged();
		notifyObservers(o);
	}
}
