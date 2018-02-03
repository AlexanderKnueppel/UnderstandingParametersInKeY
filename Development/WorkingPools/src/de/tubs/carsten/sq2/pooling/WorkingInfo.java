package de.tubs.carsten.sq2.pooling;

public class WorkingInfo {

	private final String identifier;
	private final Runnable currentJob;

	public WorkingInfo(String identifier, Runnable currentJob) {
		super();
		this.identifier = identifier;
		this.currentJob = currentJob;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Runnable getCurrentJob() {
		return currentJob;
	}

}
