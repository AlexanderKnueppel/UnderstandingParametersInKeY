package de.carsten.key.result;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.carsten.key.network.Job;
import de.carsten.key.options.OptionableContainer;
import de.carsten.key.options.strategy.KeyStrategyOptions;
import de.carsten.key.options.taclets.KeyTacletOptions;

public class OpenToResult {
	private final static File INPUT = new File("open.txt");
	private final static File OUTPUT = new File("open_csv.txt");
	
	private static final String A = "StopAtDefault";
	private static final String B = "StopAtUnclosable";

	private static BufferedWriter BW;

	public static void main(String[] args) throws IOException {
		BW = new BufferedWriter(new FileWriter(OUTPUT));
		List<String> strategies = new ArrayList<>();
		List<String> taclets = new ArrayList<>();

		OptionableContainer[] os = KeyStrategyOptions.values();
		for (OptionableContainer keyStrategyOptions : os) {
			strategies.add(keyStrategyOptions.getValue());
		}
		os = KeyTacletOptions.values();
		for (OptionableContainer keyStrategyOptions : os) {
			taclets.add(keyStrategyOptions.getValue());
		}

		List<String> titel = new ArrayList<>();
		titel.add("Experiments");
		titel.add("Config");
		titel.add("Class");
		titel.add("Method");
		titel.add("Contract number");
		titel.add("Steps");
		titel.addAll(strategies);
		titel.addAll(taclets);

		addLine(titel);

		try (BufferedReader br = new BufferedReader(new FileReader(INPUT))) {
			String line;
			Gson gson = new GsonBuilder().create();
			while ((line = br.readLine()) != null) {
				Job j = gson.fromJson(line, Job.class);
//				if(j.getCode().contains(A) || j.getCode().contains(B))
					addResult(j, strategies, taclets);
			}
		}
		BW.close();
	}

	private static void addResult(Job j, List<String> strategies,
			List<String> taclets) throws IOException {
		List<String> line = new ArrayList<>();
		line.add(j.getCode());
		line.add(""+(j.getExperiments().containsKey(A) ? j.getExperiments().get(A) : j.getExperiments().get(B)));
		line.add(j.getClazz());
		line.add(j.getMethod());
		line.add(""+j.getContractNumber());
		line.add(""+j.getSo().getMaxSteps());
		for (String strategy : strategies) {
			line.add(j.getSo().getSettingsMap().get(strategy));
		}
		for (String taclet : taclets) {
			line.add(j.getSo().getTacletMap().get(taclet));
		}
		addLine(line);
	}

	private static void addLine(List<String> line) throws IOException {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String string : line) {
			if (!first) {
				sb.append("|");
			} else {
				first = false;
			}
			sb.append(string);
		}
		BW.append(sb.toString());
		BW.newLine();
	}
}
