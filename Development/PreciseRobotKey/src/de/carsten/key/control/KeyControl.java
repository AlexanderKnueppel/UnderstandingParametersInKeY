package de.carsten.key.control;

import java.io.File;
import java.util.List;

import de.carsten.key.Result;
import de.carsten.key.options.SettingsObject;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;

public interface KeyControl {

	List<Result> getResultForProof(File source, File classPath,
			String className, String methodName, int contractNumber, SettingsObject so) throws ProblemLoaderException, ProofInputException;
	
	List<Result> getResultForProof(File source, File classPath,
			String className, String methodName, SettingsObject so) throws ProblemLoaderException, ProofInputException;

	List<Result> getResultForProof(File source, File classPath,
			String className, String methodName, String[] parameters, int contractNumber, SettingsObject so) throws ProblemLoaderException, ProofInputException;
	
	List<Result> getResultForProof(File source, File classPath,
			String className, String methodName, String[] parameters, SettingsObject so) throws ProblemLoaderException, ProofInputException;

	int getNumberOfContracts(File source, File classPath,
			String className, String methodName) throws ProblemLoaderException, ProofInputException;
	
	int getNumberOfContracts(File source, File classPath,
			String className, String methodName, String[] parameters) throws ProblemLoaderException, ProofInputException;
	
}
