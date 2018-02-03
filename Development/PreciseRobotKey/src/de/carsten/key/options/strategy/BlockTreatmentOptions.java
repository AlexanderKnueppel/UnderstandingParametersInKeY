package de.carsten.key.options.strategy;

import de.carsten.key.options.OptionableContainer;

public enum BlockTreatmentOptions implements StrategyOptionable{
	CONTRACT("Contract"), EXPAND("Expand");

	private final String value;

	BlockTreatmentOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.BLOCK_TREATMENT;
	}
}
