package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {

	Map<Integer, String> frequency = new HashMap<Integer, String>();
	GraphCanvas gc;

	public Window() {

		this.setContentPane(initializeWindow());
	}

	public JPanel initializeWindow() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(new Dimension(700, 500));
		this.setLocationRelativeTo(null);
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setPreferredSize(this.getSize());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.black);

		contentPane.add(new JButton("Load"), BorderLayout.LINE_START);

		gc = new GraphCanvas(frequency);

		contentPane.add(gc, BorderLayout.CENTER);
		return contentPane;
	}

}
