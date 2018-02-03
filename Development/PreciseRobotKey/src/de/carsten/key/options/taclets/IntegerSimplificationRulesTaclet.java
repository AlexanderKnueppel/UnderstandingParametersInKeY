package de.carsten.key.options.taclets;

import de.carsten.key.options.OptionableContainer;


public enum IntegerSimplificationRulesTaclet implements TacletOptionable{

	FULL, MINIMAL;
	
	@Override
	public String getValue() {
		return getType()+":"+name().toLowerCase();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.INTEGER_SIMPLIFICATION_RULES;
	}

}
