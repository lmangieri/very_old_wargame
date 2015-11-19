package basic;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import entities.Graph;
import entities.Node;
import entities.Vertice;
import algorithm.GraphAlgorithm;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			final Main t = new Main();

			@Override
			public void run() {
				JFrame frame = new JFrame("War Game - v1");

				GameInterface gameInterface = new GameInterface();
				frame.add(gameInterface, BorderLayout.CENTER);

				frame.setSize(1200, 965);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				gameInterface.initAnimation();
				gameInterface.initGame();
				//GraphAlgorithm gr = new GraphAlgorithm(gameInterface.graph); 
				//System.out.println("min distance brasil japao => " +gr.minDistance("Brasil", "Sibéria"));
			}
		});

	}
}