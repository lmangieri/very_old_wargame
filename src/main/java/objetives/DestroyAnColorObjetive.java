package objetives;

import java.awt.Color;
import java.util.List;

import entities.Node;
import entities.Player;
import enums.ColorEnum;

public class DestroyAnColorObjetive implements Objetive {
	private Player p;
	private int identifier;
	private ColorEnum color;
	
	@Override
	public int getIdentifier() {
		return identifier;
	}
	
	@Override
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
		

	public DestroyAnColorObjetive(int identifier, Player p) {
		this.p = p; 
		this.color = p.getColorEnum();
		this.identifier = identifier;
	}
	
	@Override
	public List<Node> getTargets() {
		return p.getNodes();
	}
	
	public ColorEnum getColor() {
		return color;
	}

	public void setColor(ColorEnum color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "Destruir os exércitos "+p.getColorEnum();
	}
}
