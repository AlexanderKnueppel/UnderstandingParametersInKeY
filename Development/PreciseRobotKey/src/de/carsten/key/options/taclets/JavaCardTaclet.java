package de.carsten.key.options.taclets;

import de.carsten.key.options.OptionableContainer;


public enum JavaCardTaclet implements TacletOptionable{

	ON, OFF;
	
	@Override
	public String getValue() {
		return getType()+":"+name().toLowerCase();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.JAVA_CARD;
	}

}
