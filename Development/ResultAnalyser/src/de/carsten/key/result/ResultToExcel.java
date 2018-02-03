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

import de.carsten.key.Result;
import de.carsten.key.options.OptionableContainer;
import de.carsten.key.options.strategy.KeyStrategyOptions;
import de.carsten.key.options.taclets.KeyTacletOptions;

public class ResultToExcel {

	private final static File INPUT = new File("zwischenergebnisse.txt");
	private final static File OUTPUT = new File("csv.txt");

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
		titel.add("Class");
		titel.add("Method");
		titel.add("Contract number");
		titel.add("contract");
		titel.add("Closed?");
		titel.add("Nodes");
		titel.addAll(strategies);
		titel.addAll(taclets);

		addLine(titel);

		try (BufferedReader br = new BufferedReader(new FileReader(INPUT))) {
			String line;
			Gson gson = new GsonBuilder().create();
			while ((line = br.readLine()) != null) {
				Result res = gson.fromJson(line, Result.class);
				addResult(res, strategies, taclets);
			}
		}
		BW.close();
	}

	private static void addResult(Result res, List<String> strategies,
			List<String> taclets) throws IOException {
		List<String> line = new ArrayList<>();
		String name = res.getName();
		// "name":"java.util.ArrayList[java.util.WoCoCollection::size()].JML normal_behavior operation contract.0"
		String clazz = name.substring(0, name.indexOf("["));
		String method = name.substring(name.indexOf("::")+2, name.indexOf("]"));
		String contractNumber = name.substring(name.lastIndexOf(".")+1);
		line.add(res.getCode());
		line.add(clazz);
		line.add(method);
		line.add(contractNumber);
		line.add(res.getProof());
		line.add("" + res.isClosed());
		line.add("" + res.getSteps());
		for (String strategy : strategies) {
			line.add(res.getOptions().get(strategy));
		}
		for (String taclet : taclets) {
			line.add(res.getTaclets().get(taclet));
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
