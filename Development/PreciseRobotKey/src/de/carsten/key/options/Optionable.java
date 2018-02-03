package de.carsten.key.options;

public interface Optionable {
	
	String getValue();
	OptionableContainer getOptionableContainer();
	
	default String getOutputString(){
		return getType() + "::" + getValue()+";";
	}
	
	default String getType(){
		return getOptionableContainer().getValue();
	}
}
