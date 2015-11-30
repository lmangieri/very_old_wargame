package auxiliaryEntities;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MP3Player implements Runnable {
	private String url;
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
