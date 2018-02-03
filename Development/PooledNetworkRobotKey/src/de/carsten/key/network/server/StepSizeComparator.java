package de.carsten.key.network.server;

import java.util.Comparator;

import de.carsten.key.network.Job;

public class StepSizeComparator implements Comparator<Job>{

	@Override
	public int compare(Job o1, Job o2) {
		return Integer.compare(o1.getSo().getMaxSteps(), o2.getSo().getMaxSteps());
	}

}
