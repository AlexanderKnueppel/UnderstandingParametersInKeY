package de.carsten.key.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.carsten.key.Result;
import de.carsten.key.options.SettingsObject;
import de.uka.ilkd.key.core.KeYMediator;
import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.java.JavaInfo;
import de.uka.ilkd.key.java.abstraction.KeYJavaType;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.init.InitConfig;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.init.ProofOblInput;
import de.uka.ilkd.key.proof.io.AbstractProblemLoader;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import de.uka.ilkd.key.proof.mgt.SpecificationRepository;
import de.uka.ilkd.key.speclang.Contract;
import de.uka.ilkd.key.ui.AbstractMediatorUserInterfaceControl;

public class GuiBasedKeyControl extends AbstractKeyControl {

	/**
	 * Stellt die Schnittstelle
	 * 
	 * @param source
	 *            Die zu beweisende Quellcodeeinheit (ein Ordner)
	 * @param classPath
	 *            Ein separater ClassPath (w�rde JavaRedux �berschreiben).
	 * @param className
	 *            Der Name der Klasse, in der eine Methode bewiesen werden soll.
	 * @param methodName
	 *            Der Name der zu beweisenden Methode.
	 * @param so
	 *            Enth�lt die Optionen f�r den Beweis.
	 * @return Gibt f�r jeden Vertrag an dieser Methode ein Result-Objekt
	 *         zur�ck.
	 * @throws ProblemLoaderException
	 *             wird geworfen, sofern KeY ein Problem mit dem Laden des Codes
	 *             hat.
	 * @throws ProofInputException
	 */
	public List<Result> getResultForProof(File source, File classPath,
			String className, String methodName, String[] parameters, int contractNumber,
			SettingsObject so) throws ProblemLoaderException,
			ProofInputException {
		return executeProofsForMethod(source, classPath, className, methodName, parameters,
				contractNumber, so);
	}

	private void waitForKeyGui() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wendet die Settings auf den aktuell ausgew�hlten Befehl an.
	 * 
	 * @param m
	 * @param so
	 */
	private void applySettings(KeYMediator m, SettingsObject so) {
		waitForKeyGui();
		Proof p = m.getSelectedProof();
		applySettings(p, so);
		m.setMaxAutomaticSteps(so.getMaxSteps());
	}

	private void applyTaclets(KeYMediator m, SettingsObject so) {
		waitForKeyGui();
		Proof p = m.getSelectedProof();
		applyTaclets(p, so);
	}

	private void loadProofAndApplySettings(MainWindow main, File source,
			File classPath, String className, String methodName, String[] parameters, 
			SettingsObject so) throws ProofInputException,
			ProblemLoaderException {
		System.out.println("Loading proof to apply taclets");
		AbstractProblemLoader apl = main.getUserInterface().load(null, source,
				null, null, null, null, false);
		System.out.println("Done loading proof");
		InitConfig ic = apl.getInitConfig();
		JavaInfo ji = ic.getServices().getJavaInfo();
		KeYJavaType kjt = ji.getTypeByClassName(className);
		// ProofManagementDialog.showInstance(ic);
		if (kjt == null) {
			throw new IllegalArgumentException(new ClassNotFoundException(
					className));
		}
		SpecificationRepository specRepo = ic.getServices()
				.getSpecificationRepository();
		List<Contract> contracts = getCorrectContract(methodName, parameters, specRepo, kjt);
		Contract contract = contracts.iterator().next();
		ProofOblInput poi = contract.createProofObl(ic);
		KeYMediator mediator = main.getMediator();
		AbstractMediatorUserInterfaceControl ui = mediator.getUI();

		ui.createProof(ic, poi);
		applyTaclets(main.getMediator(), so);
		applySettings(main.getMediator(), so);
	}

	/**
	 * F�hrt alle Beweise f�r eine Methode durch.
	 * 
	 * @param source
	 *            Die zu beweisende Quellcodeeinheit (ein Ordner)
	 * @param classPath
	 *            Ein separater ClassPath (w�rde JavaRedux �berschreiben).
	 * @param className
	 *            Der Name der Klasse, in der eine Methode bewiesen werden soll.
	 * @param methodName
	 *            Der Name der zu beweisenden Methode.
	 * @param so
	 *            Enth�lt die Optionen f�r den Beweis.
	 * @return Gibt f�r jeden Vertrag an dieser Methode ein Result-Objekt
	 *         zur�ck.
	 * @throws ProblemLoaderException
	 *             wird geworfen, sofern KeY ein Problem mit dem Laden des Codes
	 *             hat.
	 * @throws ProofInputException
	 */
	private List<Result> executeProofsForMethod(File source, File classPath,
			String className, String methodName, String[] parameters, int contractNumber,
			SettingsObject so) throws ProblemLoaderException,
			ProofInputException {
		MainWindow main = MainWindow.getInstance();
		List<Result> results = new ArrayList<>();
		try {
			main.setVisible(false);
			// if (!so.getTacletMap().isEmpty())
			loadProofAndApplySettings(main, source, classPath, className,
					methodName, parameters, so);
			System.out.println("Going to load proof to proof it");
			AbstractProblemLoader apl = main.getUserInterface().load(null,
					source, null, null, null, null, false);
			System.out.println("Done loading proof");
			InitConfig ic = apl.getInitConfig();
			JavaInfo ji = ic.getServices().getJavaInfo();
			KeYJavaType kjt = ji.getTypeByClassName(className);
			// ProofManagementDialog.showInstance(ic);
			if (kjt == null) {
				throw new IllegalArgumentException(new ClassNotFoundException(
						className));
			}
			SpecificationRepository specRepo = ic.getServices()
					.getSpecificationRepository();
			List<Contract> contracts = getCorrectContract(methodName, parameters, specRepo,
					kjt);
			if (contractNumber == -1) {
				for (Contract contract : contracts) {
					results.add(getResult(main, ic, contract, so));
				}
			} else {
				results.add(getResult(main, ic, contracts.get(contractNumber),
						so));
			}
		} finally {
			main.dispose();
		}
		return results;
	}

	private Result getResult(MainWindow main, InitConfig ic, Contract contract,
			SettingsObject so) throws ProofInputException {
		ProofOblInput poi = contract.createProofObl(ic);
		KeYMediator mediator = main.getMediator();
		AbstractMediatorUserInterfaceControl ui = mediator.getUI();

		Proof p = ui.createProof(ic, poi);
		applySettings(mediator, so);
		ui.getProofControl().startAndWaitForAutoMode(p);

		return createResult(contract, p);
	}

	@Override
	public int getNumberOfContracts(File source, File classPath,
			String className, String methodName, String[] parameters) throws ProblemLoaderException,
			ProofInputException {
		MainWindow main = MainWindow.getInstance();
		try {
			main.setVisible(false);
			System.out.println("Going to load proof to proof it");
			AbstractProblemLoader apl = main.getUserInterface().load(null,
					source, null, null, null, null, false);
			System.out.println("Done loading proof");
			InitConfig ic = apl.getInitConfig();
			JavaInfo ji = ic.getServices().getJavaInfo();
			KeYJavaType kjt = ji.getTypeByClassName(className);
			// ProofManagementDialog.showInstance(ic);
			if (kjt == null) {
				throw new IllegalArgumentException(new ClassNotFoundException(
						className));
			}
			SpecificationRepository specRepo = ic.getServices()
					.getSpecificationRepository();
			List<Contract> contracts = getCorrectContract(methodName, parameters, specRepo,
					kjt);
			return contracts.size();
		} finally {
			main.dispose();
		}
	}
}
