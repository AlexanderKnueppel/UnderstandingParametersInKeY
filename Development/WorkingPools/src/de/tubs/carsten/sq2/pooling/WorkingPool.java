package de.tubs.carsten.sq2.pooling;

import java.util.Collection;
import java.util.Observer;


public interface WorkingPool{

	void addJob(Runnable r);
	void stopWorking();
	boolean isRunning();
	int getWaitingProcesses();
	void addObserver(Observer o);
	Collection<WorkingInfo> getWorkingInfo();
}
