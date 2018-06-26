package basic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO:  a dimensão ideal é pensando no padrão da folha sufite / tablet....
				Dimension tela = Toolkit.getDefaultToolkit().getScreenSize(); 
				double lar = (double) tela.getWidth()*0.8; 
				double alt = (double) tela.getHeight()*0.8; 

				
				Double da = (double) ((double)1200 / (double)lar);
				Double de = (double) ((double)950 / (double)alt);

				Double resize;
				/*
				if(da < 1.0 && de < 1.0 ) {
					resize = 1.0;
				} else { */
					if(da > de) {
						resize = da;
					} else {
						resize = de;
					}
				// }		
				
				JFrame frame = new JFrame("War Game - v2");

				GameInterface gameInterface = new GameInterface(resize);
				frame.add(gameInterface, BorderLayout.CENTER);

				int height = (int) (950 / resize);
				int width = (int) (1200/ resize);
				
				frame.setSize(width, height);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
				frame.setResizable(false);
			}
		});

	}
}