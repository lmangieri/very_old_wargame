package entities;

import java.util.ArrayList;
import java.util.List;

import objetives.Objetive;

import org.apache.commons.lang.builder.EqualsBuilder;

import enums.ColorEnum;

public class Player {

	private boolean isPlayer;
	
	private Objetive objetive;
	
	private List<Node> nodes;
	
	private ColorEnum colorEnum;
	
	public Player(ColorEnum color, boolean isPlayer) {
		this.nodes = new ArrayList<>();
		this.colorEnum = color;
		this.isPlayer = isPlayer;
	}
	
	public boolean isPlayer() {
		return isPlayer;
	}
	
	public void setIsPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}
	
	public synchronized List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public ColorEnum getColorEnum() {
		return colorEnum;
	}

	public void setColorEnum(ColorEnum color) {
		this.colorEnum = color;
	}

	public Objetive getObjetive() {
		return objetive;
	}

	public void setObjetive(Objetive objetive) {
		this.objetive = objetive;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		final Player other = (Player)obj;
		
		return new EqualsBuilder().append(getColorEnum(),other.getColorEnum()).isEquals();
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
	
	public boolean canAttack() {
		for(Node n : getNodes()) {
			if(n.getNumberOfPieces() > 1) {
				for(Vertice v : n.getVertices()) {
					if(!v.getDestiny().getPlayer().getColorEnum().equals(getColorEnum())) {
						return true;
					}
				}
			}
		}
		return false;
	}	
}
