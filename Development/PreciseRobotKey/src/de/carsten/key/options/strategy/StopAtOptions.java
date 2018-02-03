package de.carsten.key.options.strategy;

import de.carsten.key.options.OptionableContainer;

public enum StopAtOptions implements StrategyOptionable{
	DEFAULT("Default"), UNCLOSABLE("Unclosable");

	private final String value;

	StopAtOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.STOP_AT;
	}
}
