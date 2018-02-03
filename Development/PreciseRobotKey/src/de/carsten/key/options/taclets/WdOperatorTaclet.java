package de.carsten.key.options.taclets;

import de.carsten.key.options.OptionableContainer;


public enum WdOperatorTaclet implements TacletOptionable{

	L, Y, D;
	
	@Override
	public String getValue() {
		return getType()+":"+name();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.WD_OPERATOR;
	}

}
