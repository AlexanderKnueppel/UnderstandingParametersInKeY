package de.carsten.key.options;

public interface OptionableContainer {

	Optionable[] getOptions();
	String getValue();
	Optionable getDefault();
}
