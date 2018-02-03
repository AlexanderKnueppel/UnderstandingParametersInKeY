package de.carsten.key.network.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.gson.Gson;

import de.carsten.key.SampleHelper;
import de.carsten.key.network.Job;
import de.carsten.key.network.KeyProofRunnable;
import de.carsten.key.network.file.server.FileServer;
import de.carsten.key.options.SettingsObject;
import de.carsten.key.options.strategy.KeyStrategyOptions;
import de.tubs.carsten.sq2.pooling.WorkingPool;
import de.tubs.carsten.sq2.pooling.distributed.DistributedWorkingPool;
import de.tubs.carsten.sq2.pooling.distributed.NewResultNotifier;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;

public class Server implements Observer {

	public static final int PORT = 24508;
	private static final int FILE_SERVER_PORT = PORT + 1;
	private static final File DONE_FILE = new File("done.txt");
	private static final File OPEN_FILE = new File("open.txt");
	private static final File PUNISHMENT_FILE = new File("punishments.txt");

	public static void main(String[] args) throws IOException, SAXException,
			ParserConfigurationException, ProofInputException,
			ProblemLoaderException {
		List<Job> jobs;
		if (args.length == 4) {
			String source = args[0];
			String clazz = args[1];
			String method = args[2];
			File samples = new File(args[3]);

			List<SettingsObject> sos = SampleHelper.readSPLSamples(samples);
			jobs = new ArrayList<>(sos.size());
			sos.forEach(so -> jobs.add(new Job("", -1, source, null, clazz,
					method, null, so)));
			throw new IllegalArgumentException("not yet updated...");
		} else if (args.length == 1) {
			System.out.println("Going to read jobs...");
			jobs = new BatchXMLHelper().generateJobFromXML(new File(args[0]));
			filterListForDuplicates(jobs);
//			filterListForMe(jobs);
			System.out.println("So many jobs read... " + jobs.size());
		} else {
			throw new IllegalArgumentException(
					"Es müssen vier Parameter übergeben werden: Der classpath, die Klasse, die Methode und die Samples");
		}
		PunishmentTracker pt = new PunishmentTracker(PUNISHMENT_FILE);
		if (PUNISHMENT_FILE.exists()) {
			pt.updatePunishments(jobs);
		}
		if (DONE_FILE.exists()) {
			List<Job> doneJobs = readDoneJobs(DONE_FILE);
			for (Job job : doneJobs) {
				job.reinitialize();
			}
			jobs.removeAll(doneJobs);
			System.out.println("Already done some jobs... " + jobs.size()
					+ " left");
		}
		System.out.println("Sorting jobs by step size...");
		Collections.sort(jobs, new CodeComparator(KeyStrategyOptions.STOP_AT,
				KeyStrategyOptions.ONE_STEP_SIMPLIFICATION,
				KeyStrategyOptions.PROOF_SPLITTING, 
				KeyStrategyOptions.LOOP_TREATMENT));
		Collections.sort(jobs, new StepSizeComparator());
		System.out.println("Going to write open file");
		OPEN_FILE.delete();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(OPEN_FILE))) {
			Gson gson = new Gson();
			for (Job j : jobs) {
				bw.write(gson.toJson(j));
				bw.newLine();
			}
		}
		System.out.println("Open file writen");
		Set<String> whiteLists = new HashSet<>();
		for (Job j : jobs) {
			whiteLists.add(j.getSource());
			whiteLists.add(j.getClasspath());
		}
		File temp = new File(new File("Temp"), "Server");
		temp.mkdirs();
		new FileServer(FILE_SERVER_PORT, temp, whiteLists);
		new Server().start(jobs);
		System.out.println("Done entering jobs... waiting for clients");
	}

	private static void filterListForDuplicates(List<Job> joblist) {
		for (int i = 0; i < joblist.size(); i++) {
			Job a = joblist.get(i);
			for (int j = i + 1; j < joblist.size(); j++) {
				Job b = joblist.get(j);
				if (a.equals(b)) {
					joblist.remove(j);
					j--;
					a.setCode(a.getCode() + ", " + b.getCode());
					a.getExperiments().putAll(b.getExperiments());
				}
			}
		}
		if (joblist instanceof ArrayList) {
			((ArrayList<?>) joblist).trimToSize();
		}
	}

	private static void filterListForMe(List<Job> joblist) {
		for (Iterator<Job> iterator = joblist.iterator(); iterator.hasNext();) {
			Job job = iterator.next();
			if(job.getCode().equals("Basic"))
				iterator.remove();
			else if(!job.getMethod().equals("max"))
				iterator.remove();
			else if(job.getContractNumber() != 0)
				iterator.remove();
		}
	}

	private static List<Job> readDoneJobs(File f) throws FileNotFoundException,
			IOException {
		List<Job> doneJobs = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			Gson gson = new Gson();
			while ((line = br.readLine()) != null) {
				Job j = gson.fromJson(line, Job.class);
				doneJobs.add(j);
			}
		}
		return doneJobs;
	}

	private final KeyResultObserver zo;
	private final NewResultNotifier nrn;
	private WorkingPool pool;

	// private ConsoleThread console;

	public Server() throws IOException {
		nrn = new NewResultNotifier();
		pool = new DistributedWorkingPool(PORT, nrn);
		zo = new KeyResultObserver(new File("zwischenergebnisse.txt"),
				DONE_FILE, pool, FILE_SERVER_PORT, new PunishmentTracker(
						PUNISHMENT_FILE));
		nrn.addObserver(zo);
	}

	public void start(List<Job> jobs) throws IOException {
		for (Job job : jobs) {
			pool.addJob(new KeyProofRunnable(job, FILE_SERVER_PORT));
		}
		pool.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		pool.stopWorking();
		try {
			zo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
