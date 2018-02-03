package de.carsten.key.options.strategy;

import de.carsten.key.options.Optionable;
import de.carsten.key.options.OptionableContainer;

public enum ArithmeticTreatmentOptions implements StrategyOptionable{
	BASIC("Basic"), DEFOPS("DefOps"), MODEL_SEARCH("Model Search");

	private final String value;

	ArithmeticTreatmentOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.ARITHMETIC_TREATMENT;
	}
}
