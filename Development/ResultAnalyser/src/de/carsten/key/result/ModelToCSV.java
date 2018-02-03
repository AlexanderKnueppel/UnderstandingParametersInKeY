package de.carsten.key.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import de.carsten.key.generators.FeatureIdeTranslator;
import de.carsten.key.options.DefaultOptionable;
import de.carsten.key.options.Optionable;
import de.carsten.key.options.strategy.KeyStrategyOptions;
import de.carsten.key.options.strategy.StrategyOptionable;
import de.carsten.key.options.taclets.KeyTacletOptions;
import de.carsten.key.options.taclets.TacletOptionable;

public class ModelToCSV {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Map<Optionable, Float>> l = new ArrayList<>();
		List<Float> error = new ArrayList<>();
		Set<Optionable> keys = new HashSet<>();
		List<Optionable> keyList = new ArrayList<>();
		String line;

		while (!(line = sc.nextLine()).isEmpty()) {
			Result result = getResult(line);
			Map<Optionable, Float> res = result.map;
			keys.addAll(res.keySet());
			l.add(res);
			error.add(result.error);
		}
		sc.close();
		keyList.addAll(keys);
		Collections.sort(keyList, new OptComp());
		

		StringBuilder sb = new StringBuilder();
		for (Optionable optionable : keyList) {
			sb.append(optionable.getOutputString().replaceAll(";", "")).append(
					"&");
			for (int i = 0; i < 5; i++) {
				Map<Optionable, Float> map = l.get(i);
				sb.append(
						map.containsKey(optionable) ? toString(map.get(optionable)) : "0")
						.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("\\\\\n");
		}

		sb.append("TestError").append("&");
		for (int i = 0; i < 5; i++) {
			Float f = error.get(i);
			sb.append(toString(f)).append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("\\\\\n");
		
		
		for (Optionable optionable : keyList) {
			sb.append(optionable.getOutputString().replaceAll(";", "")).append(
					"&");
			for (int i = 5; i < 10; i++) {
				Map<Optionable, Float> map = l.get(i);
				sb.append(
						map.containsKey(optionable) ? toString(map.get(optionable))
								 : "0")
						.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("\\\\\n");
		}

		sb.append("TestError").append("&");
		for (int i = 5; i < 10; i++) {
			Float f = error.get(i);
			sb.append(toString(f)).append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("\\\\\n");

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(sb.toString());
	}

	private static Result getResult(String line) {
		String[] s = line.split(";");
		String modell = s[1];
		String[] faktoren = modell.split("\\+");
		Map<Optionable, Float> res = new HashMap<>();
		for (String string : faktoren) {
			String[] faktor = string.split("\\*");
			String num = faktor[0].trim();
			String name = faktor[1].trim();

			Float numF = round(Float.parseFloat(num));
			String decodedName = FeatureIdeTranslator.decode(name);
			StrategyOptionable so = KeyStrategyOptions
					.getOptionByName(decodedName);
			TacletOptionable to = KeyTacletOptions.getOptionByName(decodedName);
			Optionable goal = so == null ? to == null ? new DefaultOptionable()
					: to : so;
			res.put(goal, numF);
		}
		Result result = new Result();
		result.map = res;
		result.error = round(Float.parseFloat(s[11]));
		return result;
	}

	private static class Result {
		Map<Optionable, Float> map;
		float error;
	}
	
	private static String toString(Float f){
		return f.intValue()+"";
//		return f.toString().replaceAll("\\.", ",");
	}
	
	private static Float round(Float f){
		return new Float(Math.round(f.floatValue()));
	}
	
	private static class OptComp implements Comparator<Optionable>{

		@Override
		public int compare(Optionable o1, Optionable o2) {
			return o1.getOutputString().compareTo(o2.getOutputString());
		}
	}
}
