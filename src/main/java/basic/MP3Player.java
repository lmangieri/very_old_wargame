package basic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MP3Player implements Runnable {
	private String url;
	private FileInputStream fis;
	private Player player;
	private Thread thread;
	
	public MP3Player(String url) {
		this.url = url;
	}
	
	public void doIt() {
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		try {
			//this.inputStream =  getClass().getResourceAsStream(url);
			//BufferedInputStream bis = new BufferedInputStream(inputStream);
			//File f = new File(getClass().getResource(url).toURI());
			//FileInputStream fis = getResourceAsStream("/data.sav"); //new FileInputStream(f);
			InputStream i = MP3Player.class.getClass().getResourceAsStream(url);
			
			BufferedInputStream bis = new BufferedInputStream(i);
			this.player = new Player(bis);
			this.player.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
			System.out.println("Can't run mp3 :(  ");
		}
	}
}
