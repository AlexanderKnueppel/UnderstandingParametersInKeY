package de.carsten.key;

import java.io.File;
import java.util.List;

import de.carsten.key.control.ExampleBasedKeyControl;
import de.carsten.key.control.KeyControl;
import de.carsten.key.options.SettingsObject;
import de.carsten.key.options.strategy.ArithmeticTreatmentOptions;
import de.carsten.key.options.strategy.AutoInductionOptions;
import de.carsten.key.options.strategy.BlockTreatmentOptions;
import de.carsten.key.options.strategy.ClassAxiomRulesOptions;
import de.carsten.key.options.strategy.DependencyContractsOptions;
import de.carsten.key.options.strategy.ExpandLocalQueriesOptions;
import de.carsten.key.options.strategy.LoopTreatmentOptions;
import de.carsten.key.options.strategy.MethodTreatmentOptions;
import de.carsten.key.options.strategy.OneStepSimplificationOptions;
import de.carsten.key.options.strategy.ProofSplittingOptions;
import de.carsten.key.options.strategy.QuantifierTreatmentOptions;
import de.carsten.key.options.strategy.QueryTreatmentOptions;
import de.carsten.key.options.strategy.StopAtOptions;
import de.carsten.key.options.taclets.AssertionsTaclet;
import de.carsten.key.options.taclets.BigIntTaclet;
import de.carsten.key.options.taclets.InitialisationTaclet;
import de.carsten.key.options.taclets.IntRulesTaclet;
import de.carsten.key.options.taclets.IntegerSimplificationRulesTaclet;
import de.carsten.key.options.taclets.JavaCardTaclet;
import de.carsten.key.options.taclets.MergeGenerateIsWeakeningGoalTaclet;
import de.carsten.key.options.taclets.ModelFieldsTaclet;
import de.carsten.key.options.taclets.PermissionsTaclet;
import de.carsten.key.options.taclets.ProgramRulesTaclet;
import de.carsten.key.options.taclets.ReachTaclet;
import de.carsten.key.options.taclets.RuntimeExceptionsTaclet;
import de.carsten.key.options.taclets.SequencesTaclet;
import de.carsten.key.options.taclets.StringsTaclet;
import de.carsten.key.options.taclets.WdChecksTaclet;
import de.carsten.key.options.taclets.WdOperatorTaclet;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;

public class MainClass {

	public static void main(String[] args) throws ProblemLoaderException,
			NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, ProofInputException {

		// File source = new File(
		// "C:/Users/Carsten/Microsoft OneDrive/OneDrive/Dokumente/TU/Masterarbeit/SVN/Beweisprobleme/Offene");
		File source = new File(
				"C:/Users/Carsten/Microsoft OneDrive/OneDrive/Dokumente/TU/Masterarbeit/SVN/Sourcecode/OpenJDK6_small");
		// File source = new File(
		// "C:/Users/Carsten/Microsoft OneDrive/OneDrive/Dokumente/TU/Masterarbeit/SVN/Sourcecode/SmallOpenJDK");
		File classPath = null;
		// File classPath = new
		// File("C:/Users/Carsten/Microsoft OneDrive/OneDrive/Dokumente/TU/Masterarbeit/SVN/Sourcecode/OwnRedux2");

		KeyControl kc = new ExampleBasedKeyControl();

//		outPutProofResults(proofArrayListSize(source, classPath, kc));
//		outPutProofResults(proofTest1_1(source, classPath, kc));
//		outPutProofResults(proofTest1_1(source, classPath, kc));
//		outPutProofResults(proofTest1_1(source, classPath, kc));
//		outPutProofResults(proofTest1_1(source, classPath, kc));
//		System.out.println();
//		outPutProofResults(proofTest1_2(source, classPath, kc));
//		outPutProofResults(proofTest1_2(source, classPath, kc));
//		outPutProofResults(proofTest1_2(source, classPath, kc));
//		outPutProofResults(proofTest1_2(source, classPath, kc));
		while(true)
		outPutProofResults(proofTest2_1(source, classPath, kc));
//		outPutProofResults(proofTest2_1(source, classPath, kc));
//		outPutProofResults(proofTest2_1(source, classPath, kc));
//		outPutProofResults(proofTest2_1(source, classPath, kc));
//		System.out.println();
//		outPutProofResults(proofTest2_2(source, classPath, kc));
//		outPutProofResults(proofTest2_2(source, classPath, kc));
//		outPutProofResults(proofTest2_2(source, classPath, kc));
//		outPutProofResults(proofTest2_2(source, classPath, kc));
		// outPutProofResults(proofIndexOfEquals(source, classPath, kc));

	}

	private static void outPutProofResults(List<Result> res) {
		res.forEach(result -> {
			System.out.println(result.getName());
//			System.out.println(result.getProof());
			System.out.println(result.isClosed() ? result.getSteps()
					: "notClosed!");
//			System.out.println();
//			System.out.println("Options:");
//			result.getOptions().forEach(
//					(key, value) -> System.out.println(key + ": " + value));
//			System.out.println();
//			System.out.println("Taclets:");
//			result.getTaclets().forEach(
//					(key, value) -> System.out.println(key + ": " + value));
			System.out.println("____________________________________________");
		});
	}

	private static List<Result> proofArrayListSize(File source, File classpath,
			KeyControl kc) throws ProblemLoaderException, ProofInputException {
		SettingsObject so = new SettingsObject();

		so.setParameter(StopAtOptions.UNCLOSABLE);
		so.setParameter(ProofSplittingOptions.DELAYED);
		so.setParameter(LoopTreatmentOptions.INVARIANT);
		so.setParameter(BlockTreatmentOptions.CONTRACT);
		so.setParameter(MethodTreatmentOptions.CONTRACT);
		so.setParameter(DependencyContractsOptions.OFF);
		so.setParameter(QueryTreatmentOptions.RESTRICTED);
		so.setParameter(ExpandLocalQueriesOptions.OFF);
		so.setParameter(ArithmeticTreatmentOptions.BASIC);
		so.setParameter(QuantifierTreatmentOptions.NO_SPLITS_WITH_PROGS);
		so.setParameter(ClassAxiomRulesOptions.DELAYED);
		so.setParameter(AutoInductionOptions.OFF);

		so.setParameter(ModelFieldsTaclet.TREAT_AS_AXIOM);
		so.setParameter(JavaCardTaclet.OFF);
		so.setParameter(IntRulesTaclet.ARITHMETICS_SEMANTICS_IGNORING_OF);

		so.setMaxSteps(1000000);
		return kc.getResultForProof(source, classpath, "java.util.ArrayList", "size", so);
	}

	private static List<Result> proofIndexOfEquals(File source, File classpath,
			KeyControl kc) throws ProblemLoaderException, ProofInputException {
		SettingsObject so = new SettingsObject();

		so.setParameter(StopAtOptions.UNCLOSABLE);
		so.setParameter(ProofSplittingOptions.DELAYED);
		so.setParameter(LoopTreatmentOptions.INVARIANT);
		so.setParameter(BlockTreatmentOptions.CONTRACT);
		so.setParameter(MethodTreatmentOptions.CONTRACT);
		so.setParameter(DependencyContractsOptions.OFF);
		so.setParameter(QueryTreatmentOptions.RESTRICTED);
		so.setParameter(ExpandLocalQueriesOptions.OFF);
		so.setParameter(ArithmeticTreatmentOptions.BASIC);
		so.setParameter(QuantifierTreatmentOptions.NO_SPLITS_WITH_PROGS);
		so.setParameter(ClassAxiomRulesOptions.DELAYED);
		so.setParameter(AutoInductionOptions.OFF);

		so.setParameter(ModelFieldsTaclet.TREAT_AS_AXIOM);
		so.setParameter(JavaCardTaclet.OFF);
		so.setParameter(IntRulesTaclet.ARITHMETICS_SEMANTICS_IGNORING_OF);

		so.setMaxSteps(1000000);
		return kc.getResultForProof(source, classpath, "IndexOf",
				"indexOfEquals", so);

	}
	
	private static List<Result> proofTest1_1(File source, File classpath, KeyControl kc) throws ProofInputException, ProblemLoaderException{
		SettingsObject so = new SettingsObject();

		so.setParameter(StopAtOptions.UNCLOSABLE);
		so.setParameter(ProofSplittingOptions.FREE);
		so.setParameter(LoopTreatmentOptions.LOOP_SCOPE_INVARIANT);
		so.setParameter(BlockTreatmentOptions.CONTRACT);
		so.setParameter(MethodTreatmentOptions.CONTRACT);
		so.setParameter(DependencyContractsOptions.ON);
		so.setParameter(QueryTreatmentOptions.OFF);
		so.setParameter(ExpandLocalQueriesOptions.OFF);
		so.setParameter(ArithmeticTreatmentOptions.DEFOPS);
		so.setParameter(QuantifierTreatmentOptions.NO_SPLITS_WITH_PROGS);
		so.setParameter(ClassAxiomRulesOptions.DELAYED);
		so.setParameter(AutoInductionOptions.OFF);
		so.setParameter(OneStepSimplificationOptions.DISABLED);

		so.setParameter(AssertionsTaclet.SAFE);
		so.setParameter(InitialisationTaclet.DISABLE_STATIC_INITIALISATION);
		so.setParameter(IntRulesTaclet.JAVA_SEMANTICS);
		so.setParameter(ProgramRulesTaclet.JAVA);
		so.setParameter(RuntimeExceptionsTaclet.BAN);
		so.setParameter(JavaCardTaclet.OFF);
		so.setParameter(StringsTaclet.ON);
		so.setParameter(ModelFieldsTaclet.TREAT_AS_AXIOM);
		so.setParameter(BigIntTaclet.ON);
		so.setParameter(SequencesTaclet.OFF);
		so.setParameter(ReachTaclet.OFF);
		so.setParameter(IntegerSimplificationRulesTaclet.FULL);
		so.setParameter(PermissionsTaclet.OFF);
		so.setParameter(WdOperatorTaclet.L);
		so.setParameter(WdChecksTaclet.OFF);
		so.setParameter(MergeGenerateIsWeakeningGoalTaclet.OFF);

		so.setMaxSteps(10000); 
		return kc.getResultForProof(source, classpath, "java.util.ArrayList",
				"ArrayList", new String[]{"int"}, 0, so);
	}
	
	private static List<Result> proofTest1_2(File source, File classpath, KeyControl kc) throws ProofInputException, ProblemLoaderException{
		SettingsObject so = new SettingsObject();

		so.setParameter(StopAtOptions.DEFAULT);
		so.setParameter(ProofSplittingOptions.FREE);
		so.setParameter(LoopTreatmentOptions.LOOP_SCOPE_INVARIANT);
		so.setParameter(BlockTreatmentOptions.CONTRACT);
		so.setParameter(MethodTreatmentOptions.CONTRACT);
		so.setParameter(DependencyContractsOptions.ON);
		so.setParameter(QueryTreatmentOptions.OFF);
		so.setParameter(ExpandLocalQueriesOptions.OFF);
		so.setParameter(ArithmeticTreatmentOptions.DEFOPS);
		so.setParameter(QuantifierTreatmentOptions.NO_SPLITS_WITH_PROGS);
		so.setParameter(ClassAxiomRulesOptions.DELAYED);
		so.setParameter(AutoInductionOptions.OFF);
		so.setParameter(OneStepSimplificationOptions.DISABLED);

		so.setParameter(AssertionsTaclet.SAFE);
		so.setParameter(InitialisationTaclet.DISABLE_STATIC_INITIALISATION);
		so.setParameter(IntRulesTaclet.JAVA_SEMANTICS);
		so.setParameter(ProgramRulesTaclet.JAVA);
		so.setParameter(RuntimeExceptionsTaclet.BAN);
		so.setParameter(JavaCardTaclet.OFF);
		so.setParameter(StringsTaclet.ON);
		so.setParameter(ModelFieldsTaclet.TREAT_AS_AXIOM);
		so.setParameter(BigIntTaclet.ON);
		so.setParameter(SequencesTaclet.OFF);
		so.setParameter(ReachTaclet.OFF);
		so.setParameter(IntegerSimplificationRulesTaclet.FULL);
		so.setParameter(PermissionsTaclet.OFF);
		so.setParameter(WdOperatorTaclet.L);
		so.setParameter(WdChecksTaclet.OFF);
		so.setParameter(MergeGenerateIsWeakeningGoalTaclet.OFF);

		so.setMaxSteps(10000); 
		return kc.getResultForProof(source, classpath, "java.util.ArrayList",
				"ArrayList", new String[]{"int"}, 0, so);
	}
	
	


	private static List<Result> proofTest2_1(File source, File classpath, KeyControl kc) throws ProofInputException, ProblemLoaderException{
		SettingsObject so = new SettingsObject();

		so.setParameter(StopAtOptions.UNCLOSABLE);
		so.setParameter(ProofSplittingOptions.OFF);
		so.setParameter(LoopTreatmentOptions.LOOP_SCOPE_INVARIANT);
		so.setParameter(BlockTreatmentOptions.CONTRACT);
		so.setParameter(MethodTreatmentOptions.CONTRACT);
		so.setParameter(DependencyContractsOptions.ON);
		so.setParameter(QueryTreatmentOptions.RESTRICTED);
		so.setParameter(ExpandLocalQueriesOptions.ON);
		so.setParameter(ArithmeticTreatmentOptions.MODEL_SEARCH);
		so.setParameter(QuantifierTreatmentOptions.NONE);
		so.setParameter(ClassAxiomRulesOptions.FREE);
		so.setParameter(AutoInductionOptions.OFF);
		so.setParameter(OneStepSimplificationOptions.DISABLED);

		so.setParameter(AssertionsTaclet.SAFE);
		so.setParameter(InitialisationTaclet.DISABLE_STATIC_INITIALISATION);
		so.setParameter(IntRulesTaclet.JAVA_SEMANTICS);
		so.setParameter(ProgramRulesTaclet.JAVA);
		so.setParameter(RuntimeExceptionsTaclet.BAN);
		so.setParameter(JavaCardTaclet.OFF);
		so.setParameter(StringsTaclet.OFF);
		so.setParameter(ModelFieldsTaclet.TREAT_AS_AXIOM);
		so.setParameter(BigIntTaclet.ON);
		so.setParameter(SequencesTaclet.OFF);
		so.setParameter(ReachTaclet.OFF);
		so.setParameter(IntegerSimplificationRulesTaclet.FULL);
		so.setParameter(PermissionsTaclet.OFF);
		so.setParameter(WdOperatorTaclet.L);
		so.setParameter(WdChecksTaclet.OFF);
		so.setParameter(MergeGenerateIsWeakeningGoalTaclet.OFF);

		so.setMaxSteps(10000); 
		return kc.getResultForProof(source, classpath, "java.util.ArrayList",
				"clear", new String[]{}, 0, so);
	}
	
	private static List<Result> proofTest2_2(File source, File classpath, KeyControl kc) throws ProofInputException, ProblemLoaderException{
		SettingsObject so = new SettingsObject();

		so.setParameter(StopAtOptions.DEFAULT);
		so.setParameter(ProofSplittingOptions.OFF);
		so.setParameter(LoopTreatmentOptions.LOOP_SCOPE_INVARIANT);
		so.setParameter(BlockTreatmentOptions.CONTRACT);
		so.setParameter(MethodTreatmentOptions.CONTRACT);
		so.setParameter(DependencyContractsOptions.ON);
		so.setParameter(QueryTreatmentOptions.RESTRICTED);
		so.setParameter(ExpandLocalQueriesOptions.ON);
		so.setParameter(ArithmeticTreatmentOptions.MODEL_SEARCH);
		so.setParameter(QuantifierTreatmentOptions.NONE);
		so.setParameter(ClassAxiomRulesOptions.FREE);
		so.setParameter(AutoInductionOptions.OFF);
		so.setParameter(OneStepSimplificationOptions.DISABLED);

		so.setParameter(AssertionsTaclet.SAFE);
		so.setParameter(InitialisationTaclet.DISABLE_STATIC_INITIALISATION);
		so.setParameter(IntRulesTaclet.JAVA_SEMANTICS);
		so.setParameter(ProgramRulesTaclet.JAVA);
		so.setParameter(RuntimeExceptionsTaclet.BAN);
		so.setParameter(JavaCardTaclet.OFF);
		so.setParameter(StringsTaclet.OFF);
		so.setParameter(ModelFieldsTaclet.TREAT_AS_AXIOM);
		so.setParameter(BigIntTaclet.ON);
		so.setParameter(SequencesTaclet.OFF);
		so.setParameter(ReachTaclet.OFF);
		so.setParameter(IntegerSimplificationRulesTaclet.FULL);
		so.setParameter(PermissionsTaclet.OFF);
		so.setParameter(WdOperatorTaclet.L);
		so.setParameter(WdChecksTaclet.OFF);
		so.setParameter(MergeGenerateIsWeakeningGoalTaclet.OFF);

		so.setMaxSteps(10000); 
		return kc.getResultForProof(source, classpath, "java.util.ArrayList",
				"clear", new String[]{}, 0, so);
	}
}
