package exceptions;

import java.awt.Color;

public class PlayerWithColorNotExist extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color color;
	public PlayerWithColorNotExist(Color color) {
		this.setColor(color);
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
