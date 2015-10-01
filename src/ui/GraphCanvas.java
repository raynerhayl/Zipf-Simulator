package ui;

import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

public class GraphCanvas extends JPanel {

	List<Integer> frequency;

	int BARWIDTH = 20;
	double YSCALE = 0.5;
	
	public GraphCanvas(List<Integer> frequency) {
		this.frequency = frequency;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawLine(0, 0, 100, 100);
	}

}
