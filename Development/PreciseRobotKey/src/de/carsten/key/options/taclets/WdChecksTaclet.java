package de.carsten.key.options.taclets;

import de.carsten.key.options.OptionableContainer;


public enum WdChecksTaclet implements TacletOptionable{

	ON, OFF;
	
	@Override
	public String getValue() {
		return getType()+":"+name().toLowerCase();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.WD_CHECKS;
	}

}
