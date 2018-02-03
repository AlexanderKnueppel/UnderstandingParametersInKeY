package de.carsten.key.options.strategy;

import de.carsten.key.options.Optionable;
import de.carsten.key.options.OptionableContainer;

public enum AutoInductionOptions implements StrategyOptionable{
	ON("On"), RESTRICTED("Restricted"), OFF("Off");

	private final String value;

	AutoInductionOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.AUTO_INDUCTION;
	}
}
