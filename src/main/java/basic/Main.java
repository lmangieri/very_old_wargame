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
				Dimension tela = Toolkit.getDefaultToolkit().getScreenSize(); 
				int lar = (int) tela.getWidth(); 
				int alt = (int) tela.getHeight();
				
				
				Double da = (double) ((double)1200 / (double)lar);
				Double de = (double) ((double)965 / (double)alt);

				System.out.println(da +" "+de);
				Double resize;
				if(da < 1.0 && de < 1.0 ) {
					resize = 1.0;
				} else {
					if(da > de) {
						resize = da;
					} else {
						resize = de;
					}
				}
				
				
				System.out.println("largura "+lar+ " alt "+alt );				
				
				JFrame frame = new JFrame("War Game - v1");

				GameInterface gameInterface = new GameInterface(resize);
				frame.add(gameInterface, BorderLayout.CENTER);

				int height = (int) (1200 / resize);
				int width = (int) (1200/ resize);
				
				frame.setSize(height, width);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
				frame.setResizable(true);
			}
		});

	}
}