package de.carsten.key.result;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.carsten.key.Result;
import de.carsten.key.options.OptionableContainer;
import de.carsten.key.options.strategy.KeyStrategyOptions;
import de.carsten.key.options.taclets.KeyTacletOptions;
import de.carsten.key.result.NameAnalyseHelper.NameAnalyseHelperResult;

public class ExperimentToExcel {

	private final static File INPUT = new File("zwischenergebnisse.txt");
	private final static File TEX_FILE = new File("tex.txt");
	private final static Map<Result, Integer> NUMBER = new HashMap<>();

	private static final EFR[] efrs = new EFR[]{
		new EFR(new File("exp_01_02.txt"), "Stop at", "StopAtDefault", "StopAtUnclosable"),
		new EFR(new File("exp_03_04.txt"), "One Step Simplification", "OneStepSimplificationEnabled", "OneStepSimplificationDisabled"),
		new EFR(new File("exp_05_06.txt"), "Proof Splitting", "ProofSplittingDelayed", "ProofSplittingFree"),
		new EFR(new File("exp_07_09.txt"), "Proof Splitting", "ProofSplittingOff", "ProofSplittingFree"),
		new EFR(new File("exp_08_10.txt"), "Proof Splitting", "ProofSplittingOff", "ProofSplittingDelayed"),
		new EFR(new File("exp_11_12.txt"), "Loop Treatment", "LoopTreatmentInvariant", "LoopTreatmentLoopScopeInvariant"),
		new EFR(new File("exp_13_14.txt"), "Dependency Contracts", "DependencyContractsOn", "DependencyContractsOff"),
		new EFR(new File("exp_15_17.txt"), "Query Treatment", "QueryTreatmentOn", "QueryTreatmentRestricted", true, 0),
		new EFR(new File("exp_16_18.txt"), "Query Treatment", "QueryTreatmentOn", "QueryTreatmentOff", true, 0),
		new EFR(new File("exp_19.txt"), "Query Treatment", "QueryTreatmentOff", "QueryTreatmentRestricted"),
		new EFR(new File("exp_20_21.txt"), "Query Treatment", "QueryTreatmentRestricted", "QueryTreatmentOn"),
		new EFR(new File("exp_22.txt"), "Expand Local Queries", "ExpandLocalQueriesOn", "ExpandLocalQueriesOff"),
		new EFR(new File("exp_23.txt"), "Expand Local Queries", "ExpandLocalQueriesOn", "ExpandLocalQueriesOff", true, 1),
		new EFR(new File("exp_24.txt"), "Arithmetic Treatment", "ArithmeticTreatmentBasic", "ArithmeticTreatmentDefOps"),
		new EFR(new File("exp_25.txt"), "Arithmetic Treatment", "ArithmeticTreatmentDefOps", "ArithmeticTreatmentModelSearch"),
		new EFR(new File("exp_26_29.txt"), "Quantifier Treatment", "QuantifierTreatmentNone", "QuantifierTreatmentNoSplits", true, 2),
		new EFR(new File("exp_27_30.txt"), "Quantifier Treatment", "QuantifierTreatmentNone", "QuantifierTreatmentNoSplitsWithProgs", true, 2),
		new EFR(new File("exp_28_31.txt"), "Quantifier Treatment", "QuantifierTreatmentNone", "QuantifierTreatmentFree", true, 2),
		new EFR(new File("exp_32_37.txt"), "Quantifier Treatment", "QuantifierTreatmentNone", "QuantifierTreatmentNoSplits"),
		new EFR(new File("exp_33_36.txt"), "Quantifier Treatment", "QuantifierTreatmentNoSplits", "QuantifierTreatmentNoSplitsWithProgs"),
		new EFR(new File("exp_34_35.txt"), "Quantifier Treatment", "QuantifierTreatmentNoSplitsWithProgs", "QuantifierTreatmentFree"),
		new EFR(new File("exp_38_40.txt"), "Class Axiom Rule", "ClassAxiomRuleFree", "ClassAxiomRuleDelayed", true, 5),
		new EFR(new File("exp_39_41.txt"), "Class Axiom Rule", "ClassAxiomRuleFree", "ClassAxiomRuleOff", true, 5),
		new EFR(new File("exp_42_43.txt"), "Strings", "StringsOn", "StringsOff"),
		new EFR(new File("exp_44_45.txt"), "Bigint", "BigintOn", "BigintOff"),
		new EFR(new File("exp_46_47.txt"), "Integersimplificationrules", "IntegerSimplificationRulesFull", "IntegerSimplificationRulesMinimal"),
		new EFR(new File("exp_48_49.txt"), "Sequences", "SequencesOn", "SequencesOff"),
		new EFR(new File("exp_50_51.txt"), "MoreSeqRules", "MoreSeqRulesOn", "MoreSeqRulesOff"),
		new EFR(new File("exp_52.txt"), "ClassAxiomRule", "ClassAxiomRuleOff", "ClassAxiomRuleDelayed", true, 3),
		new EFR(new File("exp_53.txt"), "ClassAxiomRule", "ClassAxiomRuleOff", "ClassAxiomRuleDelayed", true, 4),
	};

	private static BufferedWriter BW;
	private static BufferedWriter TEX;

	private static final List<Result[]> resultList = new ArrayList<>();
	private static final List<List<String>> lines = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		for (EFR efr : efrs) {
			try{
			genFile(efr);
			} finally {
				BW = null;
				TEX = null;
				resultList.clear();
				lines.clear();
				NUMBER.clear();
			}
		}
	}
	
	private static void genFile(EFR efr) throws IOException{
		BW = new BufferedWriter(new FileWriter(efr.getOutput()));
		List<String> strategies = new ArrayList<>();
		List<String> taclets = new ArrayList<>();

		OptionableContainer[] os = KeyStrategyOptions.values();
		Pattern aPattern = Pattern.compile(".*"+efr.getA()+"(?:,.*|$)");
		Pattern bPattern = Pattern.compile(".*"+efr.getB()+"(?:,.*|$)");
		for (OptionableContainer keyStrategyOptions : os) {
			if (keyStrategyOptions.getValue().contains(efr.getLookat()))
				continue;
			strategies.add(keyStrategyOptions.getValue());
		}
		os = KeyTacletOptions.values();
		for (OptionableContainer keyStrategyOptions : os) {
			if (keyStrategyOptions.getValue().contains(efr.getLookat()))
				continue;
			taclets.add(keyStrategyOptions.getValue());
		}

		List<String> titel = new ArrayList<>();
		titel.add("ExpNum");
		titel.add("Class");
		titel.add("Method");
		titel.add("Contract number");
		titel.add("Contract kind");
		// titel.add("contract");
//		titel.add("Closed? Equals?");
//		titel.add("Number-"+A);
//		titel.add("ExpConfig-"+A);
		titel.add("Closed?-" + efr.getA());
		titel.add("Nodes-" + efr.getA());
//		titel.add("Number-"+B);
//		titel.add("ExpConfig-"+B);
		titel.add("Closed?-" + efr.getB());
		titel.add("Nodes-" + efr.getB());
//		titel.addAll(strategies);
//		titel.addAll(taclets);

		addLine(titel);

		List<Result> results = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(INPUT))) {
			int x = 0;
			String line;
			Gson gson = new GsonBuilder().create();
			while ((line = br.readLine()) != null) {
				Result res = gson.fromJson(line, Result.class);
				NUMBER.put(res, x++);
				if(efr.isFilter() && omit(efr, res))
					continue;
				results.add(res);
			}
		}
		
		Equalizer equ = new EFREqualizer();
		int i;
		int j;
		for (i = 0; i < results.size(); i++) {
			Result a = results.get(i);
			Matcher aMatcher = aPattern.matcher(a.getCode());
			if (!aMatcher.matches())
				continue;
			for (j = 0; j < results.size(); j++) {
				Result b = results.get(j);
				Matcher bMatcher = bPattern.matcher(b.getCode());
				if (!bMatcher.matches())
					continue;
				if (equ.equals(efr, a, b)) {
					boolean gotA;
					boolean gotB;
					int firstPos = i;
					int secondPos = j;
					if(i < j) secondPos--;
					gotA = a == results.remove(firstPos);
					gotB = b == results.remove(secondPos);
					if (!gotA || !gotB)
						throw new IllegalStateException();
					resultList.add(new Result[] { a, b });
					 addResult(efr, a, b, strategies, taclets);
					 if(i > j) i--;
					 i--;
					 break;
				}
			}
		}
		TEX = new BufferedWriter(new FileWriter(TEX_FILE));
		Collections.sort(lines, new LineComparator());
		for (List<String> l : lines) {
			addLine(l);
		}
		BW.close();
		TEX.close();
		TEX = null;
		lines.clear();

		for (Result result : results) {
			Matcher aMatcher = aPattern.matcher(result.getCode());
			Matcher bMatcher = bPattern.matcher(result.getCode());
			if (aMatcher.matches() || bMatcher.matches())
//				System.out.println("Result left "+ NUMBER.get(result)+": " + result.isClosed() + ", "
//						+ result.getSteps() + " | " + result.getCode() + ": "
//						+ result.getName());
			if (aMatcher.matches())
				resultList.add(new Result[] { result, null });
			else if (bMatcher.matches())
				resultList.add(new Result[] { null, result });
		}
		
		BW = new BufferedWriter(new FileWriter(new File("allFiles.txt")));
		addLine(titel);
		for (Result[] resArr : resultList) {
			addResult(efr,resArr[0], resArr[1], strategies, taclets);
		}
		Collections.sort(lines, new LineComparator());
		for (List<String> l : lines) {
			addLine(l);
		}
		BW.close();
	}
	
	private static void addResult(EFR efr, Result a, Result b,
			List<String> strategies, List<String> taclets)
			throws IOException {
		List<String> line = new ArrayList<>();
		String name = (a == null ? b.getName() : a.getName());
		// "name":"java.util.ArrayList[java.util.WoCoCollection::size()].JML normal_behavior operation contract.0"
		NameAnalyseHelperResult nahr = NameAnalyseHelper.analyse(name);
		Integer expA = a == null ? null : a.getExperiments().get(efr.getA());
		Integer expB = b == null ? null : b.getExperiments().get(efr.getB());
		line.add(toTwoNumberString(expA != null ? expA : expB));
		line.add(nahr.getClazz());
		line.add(nahr.getMethod());
		line.add(""+nahr.getNumber());
		line.add(nahr.getKind());
		// line.add(resDef.getProof());
//		line.add("" + ((a == null || b == null) ? "" :(a.isClosed() == b.isClosed())));
//		line.add("" + (a == null ? "" : NUMBER.get(a)));
//		line.add("" + (a == null ? "" : a.getExperiments().get(A)));
		line.add("" + (a == null ? "" : a.isClosed()?"x":" "));
		line.add("" + (a == null ? -1 : a.getSteps()));
//		line.add("" + (b == null ? "" : NUMBER.get(b)));
//		line.add("" + (b == null ? "" : b.getExperiments().get(B)));
		line.add("" + (b == null ? "" : b.isClosed()?"x":" "));
		line.add("" + (b == null ? -1 : b.getSteps()));
//		if(a == null) a = b;
//		for (String strategy : strategies) {
//			line.add(a.getOptions().get(strategy));
//		}
//		for (String taclet : taclets) {
//			line.add(a.getTaclets().get(taclet));
//		}
//		addLine(line);
		lines.add(line);
	}
	
	private static boolean omit(EFR efr, Result res){
		NameAnalyseHelperResult nahr = NameAnalyseHelper.analyse(res.getName());
		String clazz = nahr.getClazz();
		String methodName = nahr.getMethod();
		int contractNum = nahr.getNumber();
		String contractType = nahr.getKind();
		if(efr.getFilterOpt() == 0){
			if (methodName.equals("max") || methodName.equals("abs") || methodName.equals("min") )
				return false;
			if (methodName.startsWith("ArrayList") || methodName.startsWith("isEmpty") || 
					methodName.startsWith("size") || methodName.startsWith("rangeCheck") || 
					methodName.startsWith("outOfBoundsMsg") || methodName.startsWith("isEmpty") ||
					methodName.startsWith("ensureCapacity"))
				return false;
			if ((methodName.startsWith("clear") || methodName.startsWith("contains") ||
					methodName.startsWith("remove") || methodName.startsWith("add")) && contractType.equals("L"))
				return false;
			return true;
		} else if(efr.getFilterOpt() == 1){
			EFR newEFR = new EFR(efr.getOutput(), efr.getLookat(), efr.getA(), efr.getB(), true, 0);
			if(!omit(newEFR, res)  || methodName.startsWith("clear"))
					return false;
			return true;
		} else if(efr.getFilterOpt() == 2){
			if (methodName.startsWith("max") || methodName.startsWith("abs") || methodName.startsWith("min") )
				return false;
			if (methodName.startsWith("ArrayList") || methodName.startsWith("isEmpty") || 
					methodName.startsWith("size") || methodName.startsWith("rangeCheck") || 
					methodName.startsWith("outOfBoundsMsg") || methodName.startsWith("isEmpty") ||
					methodName.startsWith("clear") || methodName.startsWith("contains") || 
					methodName.startsWith("add"))
				return false;
			if (methodName.startsWith("ensureCapacity") && contractNum == 0)
				return false;
			return true;
		} else if(efr.getFilterOpt() == 3){
			if(!clazz.contains("Math"))
				return false;
			return true;
		} else if(efr.getFilterOpt() == 4){
			if(clazz.contains("Math"))
				return false;
			return true;
		} else if(efr.getFilterOpt() == 5){
			if(clazz.contains("Math"))
				return false;
			return true;
		}
		throw new NotImplementedException();
	}

	private static void addLine(List<String> line) throws IOException {
		if (BW != null) addLine(line, "|", "", BW);
		if (TEX != null) addLine(line, " & ", "\\\\", TEX);
	}
	
	private static void addLine(List<String> line, String separator, String eol, BufferedWriter bw) throws IOException{
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String string : line) {
			if (!first) {
				sb.append(separator);
			} else {
				first = false;
			}
			sb.append(string);
		}
		sb.append(eol);
		bw.append(sb.toString());
		bw.newLine();
	}

	private static interface Equalizer {
		public boolean equals(EFR efr, Result a, Result b);
	}
	
	private static String toTwoNumberString(int i){
		if(i < 10){
			return "0"+i;
		} else {
			return ""+i;
		}
	}

	private static class EFREqualizer implements Equalizer {

		@Override
		public boolean equals(EFR efr, Result a, Result b) {
			if (a.getName().equals(b.getName())
					&& a.getProof().equals(b.getProof())) {
				return a.getExperiments().get(efr.getA()).equals(b.getExperiments().get(efr.getB()));
//				for (Entry<String, String> taclet : a.getTaclets().entrySet()) {
//					if (!taclet.getKey().equals(LOOKAT)
//							&& !b.getTaclets().get(taclet.getKey())
//									.equals(taclet.getValue()))
//						return false;
//				}
//				for (Entry<String, String> options : a.getOptions().entrySet()) {
//					if (!options.getKey().equals(LOOKAT)
//							&& !b.getOptions().get(options.getKey())
//									.equals(options.getValue()))
//						return false;
//				}
//				return true;
			}
			return false;
		}

	}
	
	private static class LineComparator implements Comparator<List<String>>{

		@Override
		public int compare(List<String> o1, List<String> o2) {
			if(o1.size() < 4 || o2.size() < 4){
				throw new IllegalArgumentException(o1.toString());
			}
			
			int c = 0;
			for(int num : new int[]{1,2,0,4,3}){
				c = o1.get(num).compareTo(o2.get(num));
				if (c != 0) break;
			}
			return c;
		}
	}
	
	private static final class EFR{
		private final File output;
		private final String lookat;
		private final String a;
		private final String b;
		private final boolean filter;
		private final int filterOpt;
		public EFR(File output, String lookat, String a, String b,
				boolean filter, int filterOpt) {
			super();
			this.output = output;
			this.lookat = lookat;
			this.a = a;
			this.b = b;
			this.filter = filter;
			this.filterOpt = filterOpt;
		}
		public EFR(File output, String lookat, String a, String b) {
			this(output, lookat, a, b, false, -1);
		}
		public File getOutput() {
			return output;
		}
		public String getLookat() {
			return lookat;
		}
		public String getA() {
			return a;
		}
		public String getB() {
			return b;
		}
		public boolean isFilter() {
			return filter;
		}
		public int getFilterOpt() {
			return filterOpt;
		}
	}
}
