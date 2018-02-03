package de.carsten.key.result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.carsten.key.Result;
import de.carsten.key.generators.FeatureIdeTranslator;
import de.carsten.key.options.OptionableContainer;
import de.carsten.key.options.strategy.KeyStrategyOptions;
import de.carsten.key.options.strategy.StrategyOptionable;
import de.carsten.key.options.taclets.KeyTacletOptions;
import de.carsten.key.options.taclets.TacletOptionable;

public class ResultToSPL {
	private final static File INPUT = new File("zwischenergebnisse.txt");
	private static final List<String> STRATEGIES = new ArrayList<>();
	private static final List<String> TACLETS = new ArrayList<>();
	private static final boolean USE_2_VM = false;

	private static final int SPLITS = 10;

	public static void main(String[] args) throws FileNotFoundException,
			IOException, ParserConfigurationException, TransformerException {

		OptionableContainer[] os = KeyStrategyOptions.values();
		for (OptionableContainer keyStrategyOptions : os) {
			STRATEGIES.add(keyStrategyOptions.getValue());
		}
		os = KeyTacletOptions.values();
		for (OptionableContainer keyStrategyOptions : os) {
			TACLETS.add(keyStrategyOptions.getValue());
		}

		List<Result> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(INPUT))) {
			String line;
			Gson gson = new GsonBuilder().create();
			while ((line = br.readLine()) != null /* && data.size() < 50 */) {
				Result res = gson.fromJson(line, Result.class);
				if (res.isClosed())
					data.add(res);
			}
		}
		Collections.shuffle(data);

		int blockSize = data.size() / SPLITS;
		List<List<Result>> blocks = new ArrayList<>();
		int current = 0;
		for (int i = 1; i <= SPLITS; i++) {
			int end = current + blockSize;
			List<Result> block = new ArrayList<>();
			blocks.add(block);
			for (int j = current; j < end; j++) {
				block.add(data.get(j));
			}
			current = end;
		}
		for (; current < data.size(); current++) {
			blocks.get(blocks.size() - 1).add(data.get(current));
		}

		for (int i = 0; i < SPLITS; i++) {
			List<Result> test = blocks.get(i);
			List<Result> train = new ArrayList<>();
			for (int j = 0; j < blocks.size(); j++) {
				if (i == j)
					continue;
				train.addAll(blocks.get(j));
			}
			createDocs(i + 1, train, test);
		}
	}

	private static void createDocs(int number, List<Result> train,
			List<Result> test) throws ParserConfigurationException,
			FileNotFoundException, IOException, TransformerException {
		File trainFile = new File(number + "train.xml");
		File testFile = new File(number + "test.xml");

		createDocument(trainFile, train);
		createDocument(testFile, test);
	}

	private static void createDocument(File f, List<Result> data)
			throws TransformerException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement("results");
		doc.appendChild(root);

		for (Result res : data) {
			appendRow(doc, root, res);
		}
		writeOut(doc, f);
	}

	private static void appendRow(Document doc, Element root, Result res) {
		Element row = doc.createElement("row");

		StringBuilder sb = new StringBuilder();
		sb.append("root");
		if (!USE_2_VM) {
			for (String o : STRATEGIES) {
				sb.append(",");
				StrategyOptionable so = KeyStrategyOptions.getOption(o, res
						.getOptions().get(o));
				sb.append(FeatureIdeTranslator.encode(so
						.getOptionableContainer().getValue()
						.replaceAll(";", "")));
			}
			for (String o : TACLETS) {
				sb.append(",");
				TacletOptionable to = KeyTacletOptions.getOption(o, res
						.getTaclets().get(o));
				sb.append(FeatureIdeTranslator.encode(to
						.getOptionableContainer().getValue()
						.replaceAll(";", "")));
			}
		}

		for (String o : STRATEGIES) {
			sb.append(",");
			StrategyOptionable so = KeyStrategyOptions.getOption(o, res
					.getOptions().get(o));
//			if (!USE_2_VM) {
//				sb.append(FeatureIdeTranslator.encode(so
//						.getOptionableContainer().getValue()
//						.replaceAll(";", "")));
//				sb.append(",");
//			}
			sb.append(FeatureIdeTranslator.encode(so.getOutputString()
					.replaceAll(";", "")));
		}
		for (String o : TACLETS) {
			sb.append(",");
			TacletOptionable to = KeyTacletOptions.getOption(o, res
					.getTaclets().get(o));
//			if (!USE_2_VM) {
//				sb.append(FeatureIdeTranslator.encode(to
//						.getOptionableContainer().getValue()
//						.replaceAll(";", "")));
//				sb.append(",");
//			}
			sb.append(FeatureIdeTranslator.encode(to.getOutputString()
					.replaceAll(";", "")));
		}

		appendData(doc, row, "Configuration", sb.toString());
		appendData(doc, row, "Performance", "" + res.getSteps());
		appendData(doc, row, "NumericOptions", "");
		root.appendChild(row);
	}

	private static void appendData(Document doc, Element row,
			String attributeName, String content) {
		Element data = doc.createElement("data");
		data.setAttribute("columname", attributeName);
		data.setTextContent(content);
		row.appendChild(data);
	}

	private static void writeOut(Document doc, File f)
			throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
		DOMSource source = new DOMSource(doc);
		f.delete();
		StreamResult result = new StreamResult(f);

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");

	}
}
