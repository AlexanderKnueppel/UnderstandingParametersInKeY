package de.carsten.key.options.strategy;

import de.carsten.key.options.OptionableContainer;

public enum ClassAxiomRulesOptions implements StrategyOptionable{
	FREE("Free"), DELAYED("Delayed"), OFF("Off");

	private final String value;

	ClassAxiomRulesOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.CLASS_AXIOM_RULE;
	}
}
