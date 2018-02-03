package de.carsten.key.options;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.Test;

import de.carsten.key.control.AbstractKeyControl;
import de.carsten.key.options.strategy.ArithmeticTreatmentOptions;
import de.carsten.key.options.strategy.AutoInductionOptions;
import de.carsten.key.options.strategy.BlockTreatmentOptions;
import de.carsten.key.options.strategy.ClassAxiomRulesOptions;
import de.carsten.key.options.strategy.DependencyContractsOptions;
import de.carsten.key.options.strategy.ExpandLocalQueriesOptions;
import de.carsten.key.options.strategy.LoopTreatmentOptions;
import de.carsten.key.options.strategy.MethodTreatmentOptions;
import de.carsten.key.options.strategy.OneStepSimplificationOptions;
import de.carsten.key.options.strategy.ProofSplittingOptions;
import de.carsten.key.options.strategy.QuantifierTreatmentOptions;
import de.carsten.key.options.strategy.QueryTreatmentOptions;
import de.carsten.key.options.strategy.StopAtOptions;
import de.carsten.key.options.strategy.StrategyOptionable;

public class OptionTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testOptionAvaiability() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		StrategyOptionable[][] options = new StrategyOptionable[][]{ArithmeticTreatmentOptions.values(),
				AutoInductionOptions.values(),BlockTreatmentOptions.values(),ClassAxiomRulesOptions.values(),
				DependencyContractsOptions.values(),ExpandLocalQueriesOptions.values(),LoopTreatmentOptions.values(),
				MethodTreatmentOptions.values(),ProofSplittingOptions.values(),QuantifierTreatmentOptions.values(),
				QueryTreatmentOptions.values(),StopAtOptions.values(),OneStepSimplificationOptions.values()
		};

		Field propertiesField = AbstractKeyControl.class.getDeclaredField("PROPERTIES");
		Field valuesField = AbstractKeyControl.class.getDeclaredField("VALUES");
		propertiesField.setAccessible(true);
		valuesField.setAccessible(true);
		Map<String, String> properties = (Map<String, String>) propertiesField.get(null);
		Map<String, Map<String, String>> values = (Map<String, Map<String, String>>) valuesField.get(null);
		
		for (StrategyOptionable[] optionables : options) {
			for (StrategyOptionable optionable : optionables) {
				String type = optionable.getType();
				String value = optionable.getValue();
				String prop = properties.get(type);
				assertNotNull("Type was null for "+prop, prop);
				String propValue = values.get(type).get(value);
				assertNotNull("Value was null for "+value+" of "+type, propValue);
			}
		}
	}
	
//	Test not working without loading proof... no time left right now
//	@Test
//	public void testTacletAvaiability(){
//		MainWindow main = MainWindow.getInstance();
//		main.setVisible(false);
//		ChoiceSettings csDefault = ProofSettings.DEFAULT_SETTINGS.getChoiceSettings();
//		Map<String,Set<String>> choicesMap = csDefault.getChoices();
//		List<TacletOptionable> options = new ArrayList<>();
//		for (KeyTacletOptions option : KeyTacletOptions.values()) {
//			options.addAll(Arrays.asList(option.getOptions()));
//		}
//		options.forEach(option->{
//			assertNotNull("Type was not found: "+option.getType(),choicesMap.get(option.getType()));
//			assertNotNull("Option was not found: "+option.getValue(),choicesMap.get(option.getType()).contains(option.getValue()));
//		});
//		
//	}

}
