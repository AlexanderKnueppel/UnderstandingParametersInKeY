package de.carsten.key.network.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.carsten.key.Result;
import de.carsten.key.network.Job;
import de.carsten.key.network.KeyProofRunnable;
import de.carsten.key.network.ResultCommunication;
import de.tubs.carsten.sq2.pooling.WorkingPool;

public class KeyResultObserver implements Observer {

	private BufferedWriter ergebnisse;
	private BufferedWriter doneJob;
	private Gson gson = new GsonBuilder().create();
	private final WorkingPool pool;
	private int port;
	private PunishmentTracker pt;

	public KeyResultObserver(File result, File done, WorkingPool pool, int port, PunishmentTracker pt)
			throws IOException {
		ergebnisse = new BufferedWriter(new FileWriter(result, true));
		doneJob = new BufferedWriter(new FileWriter(done, true));
		this.pool = pool;
		this.port = port;
		this.pt = pt;
	}

	public void close() throws IOException {
		ergebnisse.close();
	}

	@Override
	public void update(Observable o, Object arg) {
		ResultCommunication resCom = (ResultCommunication) arg;
		if (punish(resCom)) {
			Job j = resCom.getJob();
			try {
				j = pt.punish(j);
			} catch (IOException e) {
				e.printStackTrace();
			}
			KeyProofRunnable kpr = new KeyProofRunnable(j, port);
			pool.addJob(kpr);
		} else {
			synchronized (ergebnisse) {
				try {
					for (Result res : resCom.getResults()) {
						ergebnisse.write(gson.toJson(res));
						ergebnisse.newLine();
					}
					doneJob.write(gson.toJson(resCom.getJob()));
					doneJob.newLine();
					ergebnisse.flush();
					doneJob.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean punish(ResultCommunication resultCom) {
		final int maxSteps = resultCom.getJob().getSo().getMaxSteps();
		for (Result res : resultCom.getResults()) 
			if(!res.isClosed() && res.getSteps() >= maxSteps)
				return true;
		return false;
	}
}
