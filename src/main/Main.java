package main;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFileChooser;

import ecs100.*;

public class Main {

	Map<String, Double> frequencies = new HashMap<String, Double>();
	Map<String, Double> nFrequencies = new HashMap<String, Double>();
	List<String> orderedKeys = new ArrayList<String>();

	private final int GRAPH_WIDTH = 500;
	private final int GRAPH_HEIGHT = 300;
	private int numToGraph = 10;

	public Main() {
		UI.initialise();
		UI.addButton("Load File", () -> loadFile());
		UI.addSlider("Number to graph", 0, 100, (double v) -> adjustNumToGraph(v));
	}

	private void loadFile() {
		String fileName = UIFileChooser.open("Select File");
		try {
			Scanner scanner = new Scanner(new File(fileName));
			frequencies.clear();
			nFrequencies.clear();
			orderedKeys.clear();
			while (scanner.hasNext()) {
				String string = scanner.next().toLowerCase();
				if (frequencies.containsKey(string) == false) {
					frequencies.put(string, 1.0);
				} else {
					frequencies.put(string, frequencies.get(string) + 1);
				}
			}
			// sort frequencies
			orderedKeys = order(frequencies);
			// normalize values in the map
			nFrequencies = normalize(frequencies);
			// draw graph
			drawMap();
		} catch (IOException e) {
			System.out.println("Could not read file");
		}
	}

	public void adjustNumToGraph(double v) {
		numToGraph = (int) v;
		if (frequencies.keySet().size() > 0) {
			drawMap();
		}
		System.out.println(v);
	}

	public void drawMap() {
		UI.clearGraphics();
		final int BAR_WIDTH = GRAPH_WIDTH / numToGraph;
		UI.setColor(Color.black);
		for (int i = 0; i < orderedKeys.size(); i++) {
			int height = (int) (GRAPH_HEIGHT * nFrequencies.get(orderedKeys.get(i)));
			UI.fillRect(i * BAR_WIDTH, GRAPH_HEIGHT - height, BAR_WIDTH, height);
		}
		UI.setColor(Color.red);
		UI.drawLine(0, GRAPH_HEIGHT, GRAPH_HEIGHT, GRAPH_HEIGHT);
	}

	/**
	 * Returns a map which is the normalized copy of the one given. That is it
	 * divides all values by the largest value in the map.
	 * 
	 * @param map
	 *            the map which is to be normalized
	 * @param max
	 *            the maximum value in the map
	 * @return a map populated with the same keys but normalized values.
	 */

	private Map<String, Double> normalize(Map<String, Double> map, Double max) {
		Map<String, Double> normalized = new HashMap<String, Double>();
		for (String key : map.keySet()) {
			normalized.put(key, map.get(key) / max);
		}
		return normalized;
	}

	/**
	 * Returns a map which is the normalized copy of the one given however does
	 * not require a maximum value.
	 * 
	 * @param map
	 * @return
	 */
	private Map<String, Double> normalize(Map<String, Double> map) {
		Double max = Double.MIN_VALUE;
		String value = ""; // for testing
		for (String key : map.keySet()) {
			if (map.get(key) > max) {
				max = map.get(key);
				value = key; // for testing
			}
		}
		UI.println("Max value: " + value); // for testing
		return normalize(map, max);
	}

	/**
	 * Prints the 'num' frequent values in the map to the UI text pane.
	 * 
	 * @param orderedKeys
	 *            a list of the keys of the map in order
	 * @param map
	 *            the map whose values are to be printed
	 * @param num
	 *            the number of top frequent words to print
	 */
	private void printFrequent(List<String> orderedKeys, Map<String, Double> map, int num) {
		for (int i = 0; i < num && i < orderedKeys.size(); i++) {
			UI.println((orderedKeys.get(i)) + " " + map.get(orderedKeys.get(i)));
		}
	}

	/**
	 * Returns a list of the keys in order for a given map.
	 * 
	 * @param map
	 *            the map which is to be 'ordered'
	 * @return list of keys which correspond to values in order.
	 */
	private List<String> order(Map<String, Double> map) {
		List<Entry<String, Double>> entries = new ArrayList<Entry<String, Double>>();
		entries.addAll(map.entrySet());
		Collections.sort(entries, Entry.comparingByValue());
		List<String> keys = new ArrayList<String>();
		for (int i = entries.size() - 1; i >= 0; i--) {
			keys.add((String) entries.get(i).getKey());
		}
		return keys;
	}

	public static void main(String[] args) {
		new Main();
	}

}
