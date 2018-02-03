package de.tubs.carsten.sq2.pooling.distributed;

public interface ResultRunnable extends Runnable{

	Object getResult();
	void setIPAdress(String ip);
}
