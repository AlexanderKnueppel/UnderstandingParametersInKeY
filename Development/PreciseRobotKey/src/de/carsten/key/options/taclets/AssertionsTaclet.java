package de.carsten.key.options.taclets;

import de.carsten.key.options.OptionableContainer;


public enum AssertionsTaclet implements TacletOptionable{

	ON, OFF, SAFE;
	
	@Override
	public String getValue() {
		return getType()+":"+name().toLowerCase();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.ASSERTIONS;
	}

}
